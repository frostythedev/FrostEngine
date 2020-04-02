package me.frostythedev.developement.command;

import com.google.common.collect.Maps;

import java.util.Map;

public class Creator {

    private Map<Integer, Arg> argMap = Maps.newHashMap();

    public void addArg(int index, Arg arg) {
        if (!argMap.containsKey(index)) {
            argMap.put(index, arg);
        }
    }

    public void removeArg(int index) {
        if (argMap.containsKey(index)) {
            argMap.remove(index);
        }
    }

    public void changeArg(int index, Arg arg) {
        if (argMap.containsKey(index)) {
            argMap.replace(index, arg);
        }
    }

    public Arg getArg(int index){
        if(argMap.containsKey(index)){
            return argMap.get(index);
        }
        return null;
    }

    public boolean checkArgs(String[] args){
        if(argMap.isEmpty()){
            return true;
        }else{
            for(int i = 0; i < args.length; i++){
                Arg arg = getArg(i);
                if(arg != null){
                    if(arg.equals(Arg.INTEGER)){
                        try{
                            Integer.valueOf(args[i]);
                        }catch (NumberFormatException e){
                            return false;
                        }
                    }else if(arg.equals(Arg.DOUBLE)){
                        try{
                            Double.valueOf(args[i]);
                        }catch (NumberFormatException e){
                            return false;
                        }
                    }else if(arg.equals(Arg.RANGE)){
                        if(!args[i].contains("-")){
                            return false;
                        }
                    }
                }

            }
            return false;
        }
    }
}
