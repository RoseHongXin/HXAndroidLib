package hx.req.bean;

import java.util.List;

/**
 * Created by rose on 16-8-3.
 */

public class Pager<T> {

    public int page;
    public List<T> list;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            sb.append(list.get(i).toString() + "\n");
        }
        return sb.toString();
    }
}
