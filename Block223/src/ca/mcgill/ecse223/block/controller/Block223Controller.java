package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

public class Block223Controller {

    // private static final int paddleSpeed = 1;

    // ****************************
    // Modifier methods
    // ****************************
    public static void createGame(String name) throws InvalidInputException {
        Block223 block223 = Block223Application.getBlock223();
        UserRole user = Block223Application.getCurrentUserRole();
        Admin admin;
        if (user instanceof Admin) {
            admin = (Admin) user;
            try {
                if (Block223Application.getBlock223().findGame(name) != null) {
                    throw new InvalidInputException("The name of a game must be unique.");
                }
                new Game(name, 1, admin, 1, 1, 1, 10, 10, block223);
            } catch (RuntimeException e) {
                throw new InvalidInputException(e.getMessage());
            }
        } else {
            throw new InvalidInputException("Admin privileges are required to create a game.");
        }
    }

    public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
            Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
        UserRole currentUser = Block223Application.getCurrentUserRole();
        if (!(currentUser instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to define game settings.");
        }

        Game currentGame = Block223Application.getCurrentGame();
        if (currentGame == null) {
            throw new InvalidInputException("A game must be selected to define game settings.");
        }

        if (Block223Application.getCurrentGame().getAdmin() != currentUser) {
            throw new InvalidInputException("Only the admin who created the game can define its game settings.");
        }

        if ((nrLevels < 1) || (nrLevels > 99)) {
            throw new InvalidInputException("The number of levels must be between 1 and 99.");
        }

        if (minBallSpeedX == 0 && minBallSpeedY == 0) {
            throw new InvalidInputException("The minimum speed of the ball must be greater than zero.");
        }

        try {
            currentGame.setNrBlocksPerLevel(nrBlocksPerLevel);
            Ball ball = currentGame.getBall();
            ball.setMinBallSpeedX(minBallSpeedX);
            ball.setMinBallSpeedY(minBallSpeedY);
            ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
            Paddle paddle = currentGame.getPaddle();
            paddle.setMaxPaddleLength(maxPaddleLength);
            paddle.setMinPaddleLength(minPaddleLength);

            List<Level> levels = currentGame.getLevels();
            int size = levels.size();

            // add levels to right number
            while (nrLevels > size) {
                currentGame.addLevel();
                size = levels.size();
            }

            // removes levels to the right number
            while (nrLevels < size) {
                Level level = currentGame.getLevel(size - 1);
                level.delete();
                size = levels.size();
            }
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static void addLevelToGame() throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;

        if (!(role instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to add level.");
        } else {
            admin = (Admin) role;
        }

        Game game = Block223Application.getCurrentGame();

        if (game == null) {
            throw new InvalidInputException("A game must be selected to add level to it.");
        }

        if (admin != game.getAdmin()) {
            throw new InvalidInputException("Only the admin who created the game can add a level.");
        }

        try {
            game.addLevel();
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static void deleteGame(String name) throws InvalidInputException {
        Game game = Block223Application.getBlock223().findGame(name);

        if (game == null) {
            return;
        }
        // Checking if the current user is an admin
        if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to delete a game.");
        }

        if (game.getPublished()) {
            throw new InvalidInputException("A published game cannot be deleted.");
        }

        // Checking if the current user is the owner of the game
        if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
            throw new InvalidInputException("Only the admin who created the game can delete the game.");
        }
        if (game != null) {
            game.delete();
        }

        // Saving the file into the data file
        try {
            Block223Persistence.save(Block223Application.getBlock223());
        } catch (RuntimeException e) {

            // If there is an error when saving
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static void selectGame(String name) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to select a game.");
        }
        Game game = Block223Application.getBlock223().findGame(name);

        if (game == null) {
            throw new InvalidInputException("A game with name " + name + " does not exist.");
        }
        if (game.getAdmin() != admin) {
            throw new InvalidInputException("Only the admin who created the game can select the game.");
        }
        if (game.getPublished()) {
            throw new InvalidInputException("A published game cannot be changed.");
        }

        Block223Application.setCurrentGame(game);
    }

    public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
            Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

        // If the user is an admin
        if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to define game settings.");
        }

        // If a game isn't selected
        if (Block223Application.getCurrentGame() == null) {
            throw new InvalidInputException("A game must be selected to define game settings.");
        }

        // If the user is not the owner of the game
        if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
            throw new InvalidInputException("Only the admin who created the game can define its game settings.");
        }

        Game game = Block223Application.getCurrentGame();
        String currentName = game.getName();
        try {
            if (!currentName.equals(name)) {
                if (!game.setName(name)) {
                    throw new InvalidInputException("The name of a game must be unique.");
                }
            }
            Block223Controller.setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY,
                    ballSpeedIncreaseFactor, maxPaddleLength, minPaddleLength);
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to add a block.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to add a block.");
        }
        if (game.getAdmin() != admin) {
            throw new InvalidInputException("Only the admin who created the game can add a block.");
        }
        List<Block> blocks = game.getBlocks();
        for (Block block : blocks) {
            if (block.getRed() == red && block.getGreen() == green && block.getBlue() == blue) {
                throw new InvalidInputException("A block with the same color already exists for the game.");
            }
        }
        try {
            new Block(red, green, blue, points, game);
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static void deleteBlock(int id) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to delete a block.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to delete a block.");
        }
        if (game.getAdmin() != admin) {
            throw new InvalidInputException("Only the admin who created the game can delete a block.");
        }
        Block block = game.findBlock(id);
        if (block != null) {
            block.delete();
        }
    }

    public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to update a block.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to update a block.");
        }
        if (game.getAdmin() != admin) {
            throw new InvalidInputException("Only the admin who created the game can update a block.");
        }

        List<Block> blocks = game.getBlocks();
        for (Block block : blocks) {
            if (block.getRed() == red && block.getGreen() == green && block.getBlue() == blue) {
                if (block.getPoints() == points) {
                    throw new InvalidInputException("A block with the same color already exists for the game.");
                }
            }
        }
        Block block = game.findBlock(id);
        if (block != null) {
            try {
                block.setRed(red);
                block.setGreen(green);
                block.setBlue(blue);
                block.setPoints(points);
                return;
            } catch (RuntimeException e) {
                throw new InvalidInputException(e.getMessage());
            }

        }
        throw new InvalidInputException("The block does not exist.");
    }

    public static void updateBlockBlast(int id, int blastRadius) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to update a block.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to update a block.");
        }
        if (game.getAdmin() != admin) {
            throw new InvalidInputException("Only the admin who created the game can update a block.");
        }

        Block block = game.findBlock(id);
        if (block != null) {
            try {
                block.setBlastRadius(blastRadius);
                return;
            } catch (RuntimeException e) {
                throw new InvalidInputException(e.getMessage());
            }
        }
        throw new InvalidInputException("The block does not exist.");
    }

    public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
            throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to position a block.");
        }
        Game g = Block223Application.getCurrentGame();
        if (g == null) {
            throw new InvalidInputException("A game must be selected to position a block.");
        }
        if (!g.getAdmin().equals(admin)) {
            throw new InvalidInputException("Only the admin who created the game can position a block.");
        }
        Level levelObject;
        try {
            levelObject = g.getLevel(level - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidInputException("Level " + level + " does not exist for the game.");
        }
        if (g.getNrBlocksPerLevel() == levelObject.getBlockAssignments().size()) {
            throw new InvalidInputException("The number of blocks has reached the maximum number ("
                    + g.getNrBlocksPerLevel() + ") allowed for this game.");
        }
        for (BlockAssignment ba : levelObject.getBlockAssignments()) {
            if (ba.getGridHorizontalPosition() == gridHorizontalPosition
                    && ba.getGridVerticalPosition() == gridVerticalPosition) {
                throw new InvalidInputException("A block already exists at location " + gridHorizontalPosition + "/"
                        + gridVerticalPosition + ".");
            }
        }

        Block block = g.findBlock(id);
        if (block != null) {
            try {
                new BlockAssignment(gridHorizontalPosition, gridVerticalPosition, levelObject, block, g);
            } catch (RuntimeException e) {
                throw new InvalidInputException(e.getMessage());
            }
        } else {
            throw new InvalidInputException("The block does not exist.");
        }
    }

    public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
            int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to move a block.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to move a block.");
        }
        if (!game.getAdmin().equals(admin)) {
            throw new InvalidInputException("Only the admin who created the game can move a block.");
        }
        Level levelObject;
        try {
            levelObject = game.getLevel(level - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidInputException("Level " + level + " does not exist for the game.");
        }

        BlockAssignment blockAssignment = levelObject.findBlockAssignment(oldGridHorizontalPosition,
                oldGridVerticalPosition);
        if (blockAssignment == null) {
            throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + "/"
                    + oldGridVerticalPosition + ".");
        }
        if (levelObject.findBlockAssignment(newGridHorizontalPosition, newGridVerticalPosition) != null) {
            throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + "/"
                    + newGridVerticalPosition + ".");
        }

        try {
            blockAssignment.setGridHorizontalPosition(newGridHorizontalPosition);
            blockAssignment.setGridVerticalPosition(newGridVerticalPosition);
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }

    }

    public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
            throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to remove a block.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to remove a block.");
        }
        if (game.getAdmin() != admin) {
            throw new InvalidInputException("Only the admin who created the game can remove a block.");
        }
        Level specificLevel = game.getLevel(level - 1);
        BlockAssignment assignment = specificLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);
        if (assignment != null) {
            assignment.delete();
        }
    }

    public static void saveGame() throws InvalidInputException {
        Block223 block223 = Block223Application.getBlock223();
        if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to save a game.");
        }
        if (Block223Application.getCurrentGame() == null) {
            throw new InvalidInputException("A game must be selected to save it.");
        }
        if (Block223Application.getCurrentGame().getAdmin() != Block223Application.getCurrentUserRole()) {
            throw new InvalidInputException("Only the admin who created the game can save it.");
        }
        try {
            Block223Persistence.save(block223);
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }

    }

    public static void register(String username, String playerPassword, String adminPassword)
            throws InvalidInputException {
        Block223 block223 = Block223Application.getBlock223();

        if ((Block223Application.getCurrentUserRole() instanceof Admin)
                || (Block223Application.getCurrentUserRole() instanceof Player)) {
            throw new InvalidInputException("Cannot register a new user while a user is logged in.");
        }

        if (playerPassword != null && adminPassword != null) {
            if (playerPassword.equals(adminPassword)) {
                throw new InvalidInputException("The passwords have to be different.");
            }
        }

        try {
            Player player = new Player(playerPassword, block223);
            User user = new User(username, block223, player);
            if (adminPassword != null && !adminPassword.equals("")) {
                Admin admin = new Admin(adminPassword, block223);
                user.addRole(admin);
            }
            Block223Persistence.save(block223);
        } catch (RuntimeException e) {
            String error = e.getMessage();
            if (error.equals("Cannot create due to duplicate username")) {
                error = "The username has already been taken.";
            }
            throw new InvalidInputException(error);
        }
    }

    public static void login(String username, String password) throws InvalidInputException {
        if (Block223Application.getCurrentUserRole() != null) {
            throw new InvalidInputException("Cannot login a user while a user is already logged in.");
        }
        Block223Application.resetBlock223();
        User user = User.getWithUsername(username);
        if (user == null) {
            throw new InvalidInputException("The username and password do not match.");
        }
        List<UserRole> roles = user.getRoles();

        // String pws = "";
        for (UserRole role : roles) {
            String rolePassword = role.getPassword();
            // pws = pws + rolePassword + " / ";
            if (rolePassword.equals(password)) {
                System.out.println("Found " + role.getClass().getName());
                Block223Application.setCurrentUserRole(role);
                Block223Application.setCurrentUser(user);
                return;
            }
        }
        throw new InvalidInputException("The username and password do not match.");
    }

    public static void logout() {
        Block223Application.setCurrentUserRole(null);
        Block223Application.setCurrentUser(null);
    }

    public static void updatePaddlePosition(String userInput) {
        // PlayedGame currentGame = Block223Application.getCurrentParticularGame();
        // int levelNumber = currentGame.getCurrentLevel();
        // ParticularLevel level = currentGame.getParticularLevel(levelNumber);
        // ParticularPaddle paddle = level.getParticularPaddle();

        // double currentPosition = paddle.getPosition() + displacement;
        // if (currentPosition > Game.PLAY_AREA_SIDE - paddle.getLength() / 2) {
        // paddle.setPosition(Game.PLAY_AREA_SIDE - paddle.getLength() / 2);
        // } else if (currentPosition < paddle.getLength() / 2) {
        // paddle.setPosition(paddle.getLength() / 2);
        // } else {
        // paddle.setPosition(currentPosition);
        // }
        PlayedGame currentGame = Block223Application.getCurrentPlayableGame();
        double paddlePos = currentGame.getCurrentPaddleX();
        for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            if (c == 'l') {
                if (paddlePos >= -PlayedGame.PADDLE_MOVE_LEFT) {
                    // System.out.println("paddle left");
                    paddlePos = paddlePos + PlayedGame.PADDLE_MOVE_LEFT;
                }
            }
            if (c == 'r') {
                if (paddlePos <= Game.PLAY_AREA_SIDE - currentGame.getCurrentPaddleLength()
                        - PlayedGame.PADDLE_MOVE_RIGHT) {
                    // System.out.println("paddle right");
                    paddlePos = paddlePos + PlayedGame.PADDLE_MOVE_RIGHT;
                }
            }
            if (c == ' ') {
                break;
            }
        }
        currentGame.setCurrentPaddleX(paddlePos);
        // System.out.println(currentGame.getCurrentPaddleX());
    }

    // play mode

    public static void selectPlayableGame(String name, int id) throws InvalidInputException {
        // Game game = Game.getwithName(name);
        Block223 block223 = Block223Application.getBlock223();
        UserRole currentUser = Block223Application.getCurrentUserRole();
        Player player;
        PlayedGame pGame;
        if (currentUser instanceof Player) {
            player = (Player) currentUser;

            Game game = block223.findGame(name);
            if (game != null) {
                String username = User.findUsername(player);
                pGame = new PlayedGame(username, game, block223);
                pGame.setPlayer(player);
                // pGame.setWaitTime(16);
            } else {
                pGame = block223.findPlayableGame(id);
            }
            if (pGame == null) {
                throw new InvalidInputException("The game does not exist.");
            }
            if (game == null && !pGame.getPlayer().equals(player)) {
                throw new InvalidInputException("Only the player that started a game can continue the game.");
            }
        // } else if (((Admin) currentUser).equals(block223.findGame(name).getAdmin())) {
        //     // System.out.println("HELLOO\n");
        //     // PlayedGame pGame;
        //     Game game = block223.findGame(name);
        //     Admin admin = (Admin) currentUser;
        //     if (game != null) {
        //         String username = User.findUsername(admin);
        //         pGame = new PlayedGame(username, game, block223);
        //         // pGame.setPlayer(player);
        //         pGame.setWaitTime(16);
        //     } else {
        //         pGame = block223.findPlayableGame(id);
        //     }
        //     if (pGame == null) {
        //         throw new InvalidInputException("The game does not exist.");
        //     }
        } else {
            throw new InvalidInputException("Player privileges are required to play a game.");
        }

        Block223Application.setCurrentPlayableGame(pGame);
    }

    // public static void startGame(Block223PlayModeInterface ui) throws
    // InvalidInputException {
    // Block223 block223 = Block223Application.getBlock223();
    // UserRole currentUser = Block223Application.getCurrentUserRole();
    // if (currentUser == null) {
    // throw new InvalidInputException("Player privileges are required to play a
    // game.");
    // }
    // PlayedGame game = Block223Application.getCurrentPlayableGame();
    // if (game == null) {
    // throw new InvalidInputException("A game must be selected to play it.");
    // }
    // // Player player;
    // if (currentUser instanceof Admin) {
    // if (game.getPlayer() != null) {
    // throw new InvalidInputException("Player privileges are required to play a
    // game.");
    // }
    // if (!game.getGame().getAdmin().equals(currentUser)) {
    // throw new InvalidInputException("Only the admin of a game can test the
    // game.");
    // }
    // }
    // if (currentUser instanceof Player) {
    // if (game.getPlayer() == null) {
    // throw new InvalidInputException("Admin privileges are required to test a
    // game.");
    // }
    // }

    // if (game.getPlayStatus() == PlayStatus.Moving) {
    // System.out.println("Game was already going");
    // return;
    // }

    // String input = ui.takeInputs();
    // while (!input.contains(" ")) {
    // System.out.println("start?");
    // input = ui.takeInputs();
    // }

    // game.play();
    // while (game.getPlayStatus() == PlayStatus.Moving || game.getPlayStatus() ==
    // PlayStatus.Paused) {

    // input = ui.takeInputs();
    // // System.out.println(input);
    // // System.out.println("Paddle position: " + game.getCurrentPaddleX() + ", " +
    // game.getCurrentPaddleY());
    // // System.out.println("Ball Position: " + game.getCurrentBallX() + ", " +
    // game.getCurrentBallY());
    // // System.out.println("Ball Direction: " + game.getBallDirectionX() + ", " +
    // game.getBallDirectionY());
    // if (input.contains(" ")) {
    // switch (game.getPlayStatus())
    // {
    // case Moving:
    // System.out.println("pausing");
    // game.pause();
    // break;
    // case Paused:
    // game.play();
    // break;
    // default:
    // // Other states do respond to this event
    // }

    // }
    // updatePaddlePosition(input);
    // game.move();

    // try {
    // Thread.sleep((long) game.getWaitTime());
    // } catch (InterruptedException e) {

    // }
    // ui.refresh();

    // //System.out.println(Block223Application.getCurrentPlayableGame().getGame());
    // }
    // System.out.println("Game loop ended.");

    // if (game.getPlayStatus() == PlayStatus.GameOver) {

    // ui.endGame(game.getLives(), null);
    // Block223Application.setCurrentPlayableGame(null);

    // } else {
    // game.setBounce(null);
    // Block223Persistence.save(block223);
    // }
    // }

    public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
        Block223 block223 = Block223Application.getBlock223();
        UserRole currentUser = Block223Application.getCurrentUserRole();
        if (currentUser == null) {
            throw new InvalidInputException("Player privileges are required to play a game.");
        }
        PlayedGame game = Block223Application.getCurrentPlayableGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to play it.");
        }
        // Player player;
        if (currentUser instanceof Admin) {
            if (game.getPlayer() != null) {
                throw new InvalidInputException("Player privileges are required to play a game.");
            }
            if (!game.getGame().getAdmin().equals(currentUser)) {
                throw new InvalidInputException("Only the admin of a game can test the game.");
            }
        }
        if (currentUser instanceof Player) {
            if (game.getPlayer() == null) {
                throw new InvalidInputException("Admin privileges are required to test a game.");
            }
        }

        if (game.getPlayStatus() == PlayStatus.Moving) {
            System.out.println("Game was already going");
            return;
        }
        Game modelGame = game.getGame();

        game.play();
        String input = ui.takeInputs();
        // String input = "";
        while (game.getPlayStatus() == PlayStatus.Moving) {

            input = ui.takeInputs();
            // System.out.println(input);
            // System.out.println("Paddle position: " + game.getCurrentPaddleX() + ", " +
            // game.getCurrentPaddleY());
            // System.out.println("Ball Position: " + game.getCurrentBallX() + ", " +
            // game.getCurrentBallY());
            // System.out.println("Ball Direction: " + game.getBallDirectionX() + ", " +
            // game.getBallDirectionY());

            updatePaddlePosition(input);
            game.move();
            
            if (game.getCurrentBallX() < 0) {
                game.setCurrentBallX(0.1);
                game.setBallDirectionX(game.getBallDirectionX() * -1);
            }
            if (game.getCurrentBallX() > Game.PLAY_AREA_SIDE) {
                game.setCurrentBallX(Game.PLAY_AREA_SIDE - 0.1);
                game.setBallDirectionX(game.getBallDirectionX() * -1);
            }
            if (game.getCurrentBallY() < 0) {
                game.setCurrentBallY(0.1);
                game.setBallDirectionY(game.getBallDirectionY() * -1);
            }
            // if (game.getCurrentBallY() > Game.PLAY_AREA_SIDE) {
            //     game.setCurrentBallY(Game.PLAY_AREA_SIDE - 0.1);
            // }

            if (input.contains(" ")) {
                System.out.println("pausing");
                game.pause();
            }
            
            try {
                Thread.sleep((long) game.getWaitTime());
            } catch (InterruptedException e) {

            }
            ui.refresh();
        }
        System.out.println("Game loop ended.");

        if (game.getPlayStatus() == PlayStatus.GameOver) {
            Block223Application.setCurrentPlayableGame(null);
            HallOfFameEntry entry = modelGame.getMostRecentEntry();
            // int entryIdx = modelGame.indexOfHallOfFameEntry(entry);
            if (modelGame == null || entry == null) {
                ui.endGame(0, null);
            } else {
                ui.endGame(0, new TOHallOfFameEntry(-1, entry.getPlayername(), entry.getScore(), new TOHallOfFame(modelGame.getName())));
            }
            
        } else {
            game.setBounce(null);
        }
        Block223Persistence.save(block223);
    }

    public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
        UserRole currentUser = Block223Application.getCurrentUserRole();
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to test it.");
        }
        UserRole admin = Block223Application.getCurrentUserRole();
        if (!(admin instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to test a game.");
        }
        String username = User.findUsername(admin);
        if (Block223Application.getCurrentGame().getAdmin() != currentUser) {
            throw new InvalidInputException("Only the admin who created the game can test it.");
        }
        Block223 block223 = Block223Application.getBlock223();
        PlayedGame pgame = new PlayedGame(username, game, block223);
        pgame.setPlayer(null);
        Block223Application.setCurrentPlayableGame(pgame);
        System.out.println("Set playable game for testing");
        // startGame(ui);
    }

    public static void publishGame() throws InvalidInputException {
        UserRole currentUser = Block223Application.getCurrentUserRole();
        UserRole admin = Block223Application.getCurrentUserRole();
        Game game = Block223Application.getCurrentGame();
        if (!(admin instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to publish a game.");
        }
        if (game == null) {
            throw new InvalidInputException("A game must be selected to publish it.");
        }
        // Block223Application.currentUserRole is not admin of the game
        if (Block223Application.getCurrentGame().getAdmin() != currentUser) {
            throw new InvalidInputException("Only the admin who created the game can publish it.");
        }
        if (game.numberOfBlocks() < 1) {
            throw new InvalidInputException("At least one block must be defined for a game to be published.");
        }
        // Game game = Block223Application.getCurrentGame();
        game = Block223Application.getCurrentGame();
        game.setPublished(true);

        Block223Persistence.save(Block223Application.getBlock223());
    }

    // ****************************
    // Query methods
    // ****************************
    public static List<TOGame> getDesignableGames() throws InvalidInputException {
        // Block223 block223 = Block223Application.getBlock223();
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to access game information.");
        }
        List<TOGame> result = new ArrayList<>();
        // List<Game> games = block223.getGames();
        for (Game game : admin.getGames()) {

            if (game.isPublished()) {
                continue;
            }
            result.add(new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
                    game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
                    game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
                    game.getPaddle().getMinPaddleLength()));
        }

        return result;
    }

    public static TOGame getCurrentDesignableGame() throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to access game information.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to access its information.");
        }
        if (!game.getAdmin().equals(admin)) {
            throw new InvalidInputException("Only the admin who created the game can access its information.");
        }

        return new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
                game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
                game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
                game.getPaddle().getMinPaddleLength());
    }

    public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to access game information.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to access its information.");
        }
        if (!game.getAdmin().equals(admin)) {
            throw new InvalidInputException("Only the admin who created the game can access its information.");
        }

        List<TOBlock> result = new ArrayList<>();
        for (Block block : game.getBlocks()) {
            result.add(
                    new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints(), block.getBlastRadius()));
        }

        return result;
    }

    public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to access game information.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to access its information.");
        }
        if (!game.getAdmin().equals(admin)) {
            throw new InvalidInputException("Only the admin who created the game can access its information.");
        }
        Block block = game.findBlock(id);
        if (block != null) {
            return new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints(), block.getBlastRadius());
        }

        throw new InvalidInputException("The block does not exist.");
    }

    public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
        UserRole role = Block223Application.getCurrentUserRole();
        Admin admin;
        if (role instanceof Admin) {
            admin = (Admin) role;
        } else {
            throw new InvalidInputException("Admin privileges are required to access game information.");
        }
        Game game = Block223Application.getCurrentGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to access its information.");
        }
        if (!game.getAdmin().equals(admin)) {
            throw new InvalidInputException("Only the admin who created the game can access its information.");
        }

        Level l;
        try {
            l = game.getLevel(level - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidInputException("Level " + level + " does not exist for the game.");
        }

        List<TOGridCell> result = new ArrayList<>();
        for (BlockAssignment ba : l.getBlockAssignments()) {
            result.add(new TOGridCell(ba.getGridHorizontalPosition(), ba.getGridVerticalPosition(),
                    ba.getBlock().getId(), ba.getBlock().getRed(), ba.getBlock().getGreen(), ba.getBlock().getBlue(),
                    ba.getBlock().getPoints(), ba.getBlock().getBlastRadius()));
        }

        return result;
    }

    public static TOUserMode getUserMode() {
        UserRole role = Block223Application.getCurrentUserRole();
        if (role == null) {
            System.out.println("role is null");
            return new TOUserMode(Mode.None);
        }
        if (role instanceof Player) {
            System.out.println("role is Player");
            return new TOUserMode(Mode.Play);
        }
        if (role instanceof Admin) {
            System.out.println("role is Admin");
            return new TOUserMode(Mode.Design);
        }
        return new TOUserMode(Mode.None);
    }

    // play mode

    public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
        Block223 block223 = Block223Application.getBlock223();
        UserRole role = Block223Application.getCurrentUserRole();
        Player player;
        if (role instanceof Player) {
            player = (Player) role;
        } else {
            throw new InvalidInputException("Player privileges are required to play a game.");
        }
        List<TOPlayableGame> result = new ArrayList<>();
        List<Game> games = block223.getGames();
        for (Game g : games) {
            if (g.isPublished()) {
                // System.out.println(g.getName() + " is published");
                result.add(new TOPlayableGame(g.getName(), -1, 0));
            } else {
                // System.out.println(g.getName() + " is not published");
            }
        }

        List<PlayedGame> pGames = player.getPlayedGames();
        for (PlayedGame pg : pGames) {
            result.add(new TOPlayableGame(pg.getGame().getName(), pg.getId(), pg.getCurrentLevel()));
        }
        return result;
    }

    public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
        Block223Application.getBlock223();
        UserRole currentUser = Block223Application.getCurrentUserRole();
        if (currentUser == null) {
            throw new InvalidInputException("Player privileges are required to play a game.");
        }
        PlayedGame game = Block223Application.getCurrentPlayableGame();
        if (game == null) {
            throw new InvalidInputException("A game must be selected to play it.");
        }
        if (game.getGame() == null) {
            throw new InvalidInputException("The game of the playable game must not be null.");
        }
        // Player player;
        if (currentUser instanceof Admin) {
            if (game.getPlayer() != null) {
                throw new InvalidInputException("Player privileges are required to play a game.");
            }
        
            if (!game.getGame().getAdmin().equals(currentUser)) {
                throw new InvalidInputException("Only the admin of a game can test the game.");
            }
        }
        if (currentUser instanceof Player) {
            // player = (Player) currentUser;
            if (game.getPlayer() == null) {
                throw new InvalidInputException("Admin privileges are required to test a game.");
            }
        }
        boolean paused = game.getPlayStatus() == PlayStatus.Ready || game.getPlayStatus() == PlayStatus.Paused;
        TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(game.getGame().getName(), paused, game.getScore(),
                game.getLives(), game.getCurrentLevel(), game.getPlayername(), game.getCurrentBallX(),
                game.getCurrentBallY(), game.getCurrentPaddleLength(), game.getCurrentPaddleX());

        List<PlayedBlockAssignment> blocks = game.getBlocks();
        for (PlayedBlockAssignment b : blocks) {
            new TOCurrentBlock(b.getBlock().getRed(), b.getBlock().getGreen(), b.getBlock().getBlue(),
                    b.getBlock().getPoints(), b.getX(), b.getY(),b.getBlock().getBlastRadius(), result);
        }

        return result;
    }

    public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
        UserRole currentRole = Block223Application.getCurrentUserRole();
        if (!(currentRole instanceof Player)) {
            throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
        }
        PlayedGame pgame = Block223Application.getCurrentPlayableGame();
        if (pgame == null) {
            throw new InvalidInputException("A game must be selected to view its hall of fame.");
        }
        Game game = pgame.getGame();
        TOHallOfFame result = new TOHallOfFame(game.getName());
        List<HallOfFameEntry> hof = game.getHallOfFameEntries();
        if (start < 1) {
            start = 1;
        }
        if (end > game.numberOfHallOfFameEntries()) {
            end = game.numberOfHallOfFameEntries();
        }
        // start--;
        // end--;

        for (int i = hof.size() - start; i >= hof.size() - end; i--) {
            result.addEntry(
                    new TOHallOfFameEntry(hof.size() - i, hof.get(i).getPlayername(), hof.get(i).getScore(), result));
        }
        // for (int i = ; i<=start; i--){
        // TOHallOfFameEntry to = new TOHallOfFameEntry(iaPosition+1,
        // game.getHallOfFameEntry(i).getPlayername(),game.getHallOfFameEntry(i).getScore(),
        // result);

        // //String username =
        // User.findUsername(game.getHallOfFameEntry(i).getPlayer());
        // //TOHallOfFameEntry to = new TOHallOfFameEntry(i+1,username,
        // game.getHallOfFameEntry(i).getScore(), result);
        // // result.addEntry(to);
        // }
        return result;
    }

    public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
        UserRole currentRole = Block223Application.getCurrentUserRole();
        if (!(currentRole instanceof Player)) {
            throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
        }
        PlayedGame pgame = Block223Application.getCurrentPlayableGame();
        if (pgame == null) {
            throw new InvalidInputException("A game must be selected to view its hall of fame.");
        }
        Game game = pgame.getGame();
        TOHallOfFame result = new TOHallOfFame(game.getName());
        HallOfFameEntry mostRecent = game.getMostRecentEntry();
        int index = game.indexOfHallOfFameEntry(mostRecent);

        List<HallOfFameEntry> hof = game.getHallOfFameEntries();

        int start = index - numberOfEntries / 2;
        if (start < 1) {
            start = 1;
        }
        int end = start + numberOfEntries / 2;
        if (end > game.numberOfHallOfFameEntries()) {
            end = game.numberOfHallOfFameEntries();
        }
        // start--;
        // end--;

        for (int i = hof.size() - start; i >= hof.size() - end; i--) {
            result.addEntry(
                    new TOHallOfFameEntry(hof.size() - i, hof.get(i).getPlayername(), hof.get(i).getScore(), result));
        }
        // for (int i = end; i<=start; i--){
        // TOHallOfFameEntry to = new TOHallOfFameEntry(i+1,
        // game.getHallOfFameEntry(i).getPlayername(),
        // game.getHallOfFameEntry(i).getScore(), result);

        // //String username =
        // User.findUsername(game.getHallOfFameEntry(i).getPlayer());
        // //TOHallOfFameEntry to = new TOHallOfFameEntry(i+1, username,
        // game.getHallOfFameEntry(i).getScore(), result);
        // //result.addEntry(to);
        // }

        return result;
    }

}