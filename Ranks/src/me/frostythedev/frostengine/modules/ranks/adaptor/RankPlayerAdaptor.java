package me.frostythedev.frostengine.modules.ranks.adaptor;

import com.google.common.collect.Lists;
import com.google.gson.*;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class RankPlayerAdaptor implements JsonAdaptor<RankPlayer>{
    @Override
    public RankPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String uuidString = jsonObject.get("uuid").getAsString();
        String rankList = jsonObject.get("ranks").getAsString();

        UUID uuid = UUID.fromString(uuidString);
        RankPlayer rankPlayer = new RankPlayer(uuid);

        if(rankList.equals("")){
           return rankPlayer;
        }else{
            List<Rank> ranks = Lists.newArrayList();

            if(!rankList.contains(";")){
                Rank rank = ModuleRanks.get().getRankManager().getRank(rankList);
                if(rank != null){
                    ranks.add(rank);
                }
            }else{
                for(String rankNames : rankList.split(";")){
                    Rank rank = ModuleRanks.get().getRankManager().getRank(rankNames);
                    if(rank != null){
                        ranks.add(rank);
                    }
                }
            }

            rankPlayer.setRanks(ranks);
            return rankPlayer;
        }
    }

    @Override
    public JsonElement serialize(RankPlayer rankPlayer, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        String uuid = rankPlayer.getUuid().toString();
        List<Rank> ranks = rankPlayer.getRanks();

        jsonObject.addProperty("uuid", uuid);

        String  rankList = "";
        if(!ranks.isEmpty()){
            for(Rank rank : ranks){
                if(rankList.equals("")){
                    rankList+= rank.getName();
                }else{
                    rankList+= ";" + rank.getName();
                }
            }
        }

        jsonObject.addProperty("ranks", rankList);

        return jsonObject;
    }
}
