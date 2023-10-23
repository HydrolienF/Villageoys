package fr.formiko.villageoys;

public enum BuildingType {
    HOUSE, FARM, MINE, SMITH, BAKERY, LIBRARY, TOWER, WALL, GATE, MARKET, INN, TAVERN, CHURCH, CATHEDRAL, BARRACKS, STABLE, TOWNHALL,
    CASTLE, FORTRESS, PALACE, KEEP, MANSION, MUSEUM, ACADEMY, UNIVERSITY, LABORATORY, FACTORY, WORKSHOP, WAREHOUSE;

    @Override
    public String toString() { return name().toLowerCase(); }
    // public String toRessourcesName() { return "mvndi:" + name().toLowerCase() + "_1"; }
}
