package fr.formiko.villageoys;

public enum BuildingType {
    // @formatter:off
    // Base builing
    TOWNHALL(true), // Main building, increase max population and max storage
    HOUSE, // Contains bed for villagers, increase max population
    WAREHOUSE, // Increase max storage

    // Raw production buildings
    FARM, // Increase vegetable production (wheat, potatoes, carrot, etc)
    ENCLOSED_PLOT, // Increase animal production (sheep, cow, pig, etc)
    FOREST(4), // Increase wood production
    MINE, // Increase ores production
    QUARRY, // Increase stones production
    FISHING_DOCS, // Increase fish production

    // Processed production buildings
    SMITH,
    BAKERY,
    LIBRARY,

    // War buildings
    TOWER,
    WALL,
    GATE,
    BARRACKS,
    STABLE, // Produce horses

    // Player interaction buildings
    MARKET,
    TAVERN,

    // End game buildings
    WORKSHOP,
    CHURCH,
    CATHEDRAL(true),
    ACADEMY(true),
    UNIVERSITY(true),
    LABORATORY(true),
    WONDER(true);
    // @formatter:on

    private final boolean unique;
    private final int chunkSize;

    private BuildingType() { this(false, 1); }
    private BuildingType(boolean unique) { this(unique, 1); }
    private BuildingType(int chunkSize) { this(false, chunkSize); }
    private BuildingType(boolean unique, int chunkSize) {
        this.unique = unique;
        this.chunkSize = chunkSize;
    }

    @Override
    public String toString() { return name().toLowerCase(); }
    // public String toRessourcesName() { return "mvndi:" + name().toLowerCase() + "_1"; }

    public boolean isUnique() { return unique; }
    public int getChunkSize() { return chunkSize; }
}
