package net.workspace.workspace_backend.service;



import net.workspace.workspace_backend.model.UserDetails;
import net.workspace.workspace_backend.repository.UserDetailsRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {

    @Autowired
    UserDetailsRepo userDetailsRepo;

    public List<UserDetails> getAlluser() {

        return  userDetailsRepo.findAll();
    }


    public UserDetails createUser(UserDetails userdetails) {

        return userDetailsRepo.save(userdetails);


    }


    public String login(String mailId, String password) {

        UserDetails user = userDetailsRepo.findByMailId(mailId);

        if (user == null) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "failure");
            jsonResponse.put("message", "Credentials not exist");
            return  jsonResponse.toString();
        }

        // Check if the password matches
        if (!user.getPassword().equals(password)) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "failure");
            jsonResponse.put("message", "Password incorrect");
            return jsonResponse.toString();
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "success");
        jsonResponse.put("userName", user.getUserName());
        jsonResponse.put("mailId", user.getMailId());
        jsonResponse.put("userId", user.getUserId());


        return jsonResponse.toString();
    }
}
