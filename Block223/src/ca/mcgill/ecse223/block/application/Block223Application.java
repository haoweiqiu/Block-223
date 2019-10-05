package ca.mcgill.ecse223.block.application;

import java.awt.EventQueue;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223Page;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.PlayedGame;;

public class Block223Application {
    
    private static Block223 block223;
    private static UserRole currentUserRole;
    private static Game currentGame;
    private static User currentUser;
    private static PlayedGame currentPlayableGame;

    public static void main(String[] args) {
        block223 = getBlock223();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Block223Page();
            }
        });
    }

    public static Block223 getBlock223() {
        if (block223 == null) {
            block223 = Block223Persistence.load();
        }
        return block223;
    }

    public static Block223 resetBlock223() {
        if (block223 != null) {
            block223.delete();
        }
        setCurrentGame(null);
        setCurrentPlayableGame(null);
        setCurrentUser(null);
        setCurrentUserRole(null);
        block223 = Block223Persistence.load();
        return block223;
    }

    public static void setCurrentUserRole(UserRole aUserRole) {
        currentUserRole = aUserRole;
    }

    public static UserRole getCurrentUserRole() {
        return currentUserRole;
    }

    public static void setCurrentGame(Game aGame) {
        currentGame = aGame;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }
    
    public static String getCurrentUserName() {
        if (currentUser == null) {
            return "";
        }
        return currentUser.getUsername();
    }
    
    public static void setCurrentUser(User user) {
    	currentUser = user;
    }

    public static void setCurrentPlayableGame(PlayedGame pg) {
        currentPlayableGame = pg;
    }
    
    public static PlayedGame getCurrentPlayableGame() {
        return currentPlayableGame;
    }
}