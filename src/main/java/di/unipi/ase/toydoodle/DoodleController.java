/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.unipi.ase.toydoodle;

import di.unipi.ase.toydoodle.exceptions.ObjectNotFoundException;
import di.unipi.ase.toydoodle.exceptions.WrongMethodCallException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

/**
 *
 * @author Sushi
 */
@RestController
public class DoodleController {
    private int id = 0;
    final private Map<Integer, Doodle> doodles = new HashMap();
    
    /**
     * 
     * @return a table with the callable method.
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(){
        return "<!DOCTYPE html><html>\n"
                + "\t<table>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<th>Method</th>\n"
                + "\t\t\t<th>URL</th>\n"
                + "\t\t\t<th>Request Type</th>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Create doodle</td>\n"
                + "\t\t\t<td>/doodles</td>\n"
                + "\t\t\t<td>PUT</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Retrieve opened doodles</td>\n"
                + "\t\t\t<td>/doodles</td>\n"
                + "\t\t\t<td>GET</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Retrieve doodle.</td>\n"
                + "\t\t\t<td>/doodles/{id}</td>\n"
                + "\t\t\t<td>GET</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Delete doodle.</td>\n"
                + "\t\t\t<td>/doodles/{id}</td>\n"
                + "\t\t\t<td>DELETE</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Create vote in a doodle.</td>\n"
                + "\t\t\t<td>/doodles/{id}/vote</td>\n"
                + "\t\t\t<td>PUT</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Retrieve option voted by participant.</td>\n"
                + "\t\t\t<td>/doodles/{id}/vote/{name}</td>\n"
                + "\t\t\t<td>GET</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Update option voted by participant.</td>\n"
                + "\t\t\t<td>/doodles/{id}/vote/{name}</td>\n"
                + "\t\t\t<td>POST</td>\n"
                + "\t\t</tr>\n"
                + "\t\t<tr>\n"
                + "\t\t\t<td>Delete option voted by participant.</td>\n"
                + "\t\t\t<td>/doodles/{id}/vote/{name}</td>\n"
                + "\t\t\t<td>DELETE</td>\n"
                + "\t\t</tr>\n"
                + "\t</table>\n"
                + "</html>\n";
    }
    
    /**
     * Add a new doodle to the doodles pool.
     * @param doodle - The doodle to add.
     * @return the id of the new doodle.
     */
    @RequestMapping(value="/doodles", method = RequestMethod.PUT)
    public long createDoodles(@Valid @RequestBody Doodle doodle){
        doodles.put(id++, new Doodle(doodle));
        return id - 1;
    }
    
    /**
     * Return all the opened doodles.
     * @return the map of all opened doodles.
     */
    @RequestMapping(value="/doodles", method = RequestMethod.GET)
    public Map<Integer, Doodle> getDoodles(){
        return doodles;
    }
    
    /**
     * Delete a doodle from the pool.
     * @param id - the id of the doodle to delete.
     * @return true if the doodle is correctly deleted, false if it not exist or there was a problem.
     */
    @RequestMapping(value="/doodles/{id}", method = RequestMethod.DELETE)
    public boolean deleteDoodle(@PathVariable("id") int id){
        return doodles.remove(id) != null;
    }
    
    /**
     * Return a doodle.
     * @param id - the id of the doodle to return.
     * @return the doodle with the input id.
     */
    @RequestMapping(value="/doodles/{id}", method = RequestMethod.GET)
    public Doodle getDoodle(@PathVariable("id") int id){
        return doodles.get(id);
    }
    
    /**
     * Add a vote to the target doodle.
     * @param id - the id of the doodle to which add the vote.
     * @param vote - the vote to add.
     * @return the name of the voter if the operation succeeds, null otherwise.
     * @throws di.unipi.ase.toydoodle.exceptions.ObjectNotFoundException - if no doodle with input id is found.
     * @throws di.unipi.ase.toydoodle.exceptions.WrongMethodCallException - if a vote with the same participant already exists.
     */
    @RequestMapping(value="/doodles/{id}/vote", method = RequestMethod.PUT)
    public String addVote(@PathVariable("id") int id, @Valid @RequestBody Vote vote) throws ObjectNotFoundException, WrongMethodCallException{
        Doodle doodle =  doodles.get(id);
        
        if(doodle == null)  throw new ObjectNotFoundException("Doodle", Integer.toString(id));
        
        if(doodle.findPreviousVote(vote.getName()) != null)
            throw new WrongMethodCallException("PUT", "POST", "for updating vote.");
        
        return doodle.addVote(vote);
    }
    
    /**
     * Return the option voted by a participant.
     * @param id - the id of the doodle.
     * @param name - the name of the participant.
     * @return the option voted by the input participant.
     * @throws di.unipi.ase.toydoodle.exceptions.ObjectNotFoundException - if no doodle with input id is found.
     */
    @RequestMapping(value="/doodles/{id}/vote/{name}", method = RequestMethod.GET)
    public String getVotedOption(@PathVariable("id") int id, @PathVariable("name") String name) throws ObjectNotFoundException{
        Doodle doodle =  doodles.get(id);
        
        if(doodle == null)  throw new ObjectNotFoundException("Doodle", Integer.toString(id));
        
        return doodle.findPreviousVote(name);
    }
    
    /**
     * Delete a vote of a participant from the doodle.
     * @param id - the doodle from which delete the vote.
     * @param name - the name of the participant of which vote have to be deleted.
     * @return true if all go right, false otherwise.
     * @throws di.unipi.ase.toydoodle.exceptions.ObjectNotFoundException - if no doodle with input id is found.
     */
    @RequestMapping(value="/doodles/{id}/vote/{name}", method = RequestMethod.DELETE)
    public boolean deleteVote(@PathVariable("id") int id, @PathVariable("name") String name) throws ObjectNotFoundException{
        Doodle doodle =  doodles.get(id);
        
        if(doodle == null)  throw new ObjectNotFoundException("Doodle", Integer.toString(id));
        
        return doodle.removeVote(name);
    }
    
    /**
     * Update a vote of a participant from the doodle.
     * @param id - the id of the doodle.
     * @param name - the name of the participant of which vote have to be update.
     * @param vote - the new vote.
     * @return the name of the voter if the vote is successfully update, false otherwise.
     * @throws di.unipi.ase.toydoodle.exceptions.ObjectNotFoundException - if no doodle with input id is found.
     * @throws di.unipi.ase.toydoodle.exceptions.WrongMethodCallException - the name of the vote and the one into the URL don't match.
     */
    @RequestMapping(value="/doodles/{id}/vote/{name}", method = RequestMethod.POST)
    public String updateVote(@PathVariable("id") int id, @PathVariable("name") String name, @RequestBody Vote vote) throws ObjectNotFoundException, WrongMethodCallException{
        Doodle doodle =  doodles.get(id);
        
        if(doodle == null)  throw new ObjectNotFoundException("Doodle", Integer.toString(id));
        if(!vote.getName().equals(name)) 
            throw new WrongMethodCallException("POST", "PUT on /doodle/{id}/vote", " for inserting a new Vote. If you want to update"
                    + "a vote, the name on the URL and the one on the vote must be the same.");
        
        if(!doodle.hasAlreadyVoted(vote.getName()))    
            throw new WrongMethodCallException("POST", "PUT on /doodle/{id}/vote", " for inserting a new Vote.");
        
        return doodle.addVote(vote);
    }
}
