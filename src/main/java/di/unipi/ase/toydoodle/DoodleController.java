/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.unipi.ase.toydoodle;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sushi
 */
@RestController
public class DoodleController {
    private int id = 0;
    final private Map<Integer, Doodle> doodles = new HashMap();
    
    @RequestMapping(value="/doodles", method = RequestMethod.PUT)
    public long createDoodles(@RequestBody Doodle doodle){
        doodles.put(id++, new Doodle(doodle));
        return id - 1;
    }
    
    @RequestMapping(value="/doodles", method = RequestMethod.GET)
    public Doodle []getDoodles(){
        return doodles.values().toArray(new Doodle[0]);
    }
    
    @RequestMapping(value="/doodles/{id}", method = RequestMethod.DELETE)
    public boolean deleteDoodle(@PathVariable("id") int id){
        return doodles.remove(id) != null;
    }
    
    @RequestMapping(value="/doodles/{id}", method = RequestMethod.GET)
    public Doodle getDoodle(@PathVariable("id") int id){
        return doodles.get(id);
    }
    
    @RequestMapping(value="/doodles/{id}/vote", method = RequestMethod.GET)
    public String getDoodle(@PathVariable("id") int id, @RequestBody Vote vote){
        return doodles.get(id).addVote(vote);
    }
}
