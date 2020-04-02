package me.frostythedev.frostengine.modules.ranks.callback;

import me.frostythedev.frostengine.data.core.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerIdCallback implements Callback {

    private int id = -1;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(!rs.next()){
            return;
        }

        if(rs.getInt("id") >= 0){
            id = rs.getInt("id");
        }else{
            id = -1;
        }
    }

    @Override
    public void digestSync() {

    }

    @Override
    public void digestAsync() {

    }

    @Override
    public Object result() {
        return id;
    }
}
