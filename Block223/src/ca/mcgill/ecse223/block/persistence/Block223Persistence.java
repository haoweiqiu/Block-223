package ca.mcgill.ecse223.block.persistence;

import ca.mcgill.ecse223.block.model.Block223;

public class Block223Persistence {

    private static String filename = "data.block223";
    private static Block223 block223;

    public static void save(Block223 block223) {
    	PersistenceObjectStream.setFilename(filename);
        PersistenceObjectStream.serialize(block223);
    }

    public static Block223 load() {
        PersistenceObjectStream.setFilename(filename);
        block223 = (Block223) PersistenceObjectStream.deserialize();
        if (block223 == null) {
        	// System.out.println("new block223");
        	block223 = new Block223();
        } else {
            // System.out.println("reinitialize");
            block223.reinitialize();
        }

        return block223;
    }
}