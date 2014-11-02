package edu.qaware.cc.reactiveZwitscher.connectors.feedzilla;

/**
 * Repräsentiert die verschiedenen News-Kategorien auf Feedzilla.
 * Siehe: http://api.feedzilla.com/v1/categories.xml
 *  
 * @author Josef Adersberger
 */
public enum NewsCategory {
    
    ART(13),
    BLOGS(21),
    BUSINESS(22),
    CELEBETIES(5),
    COLUMNISTS(588),
    ENTERTAINMENT(6),
    EVENTS(17),
    FUN_STUFF(25),
    GENERAL(1168),
    HEALTH(11),
    HOBBIES(14),
    INDUSTRY(2),
    INTERNET(28),
    IT(15),
    JOBS(33),
    LAW(591),
    LIFESTYLE(20),
    MUSIC(29),
    ODDLY_ENOUGH(36),
    POLITICS(3),
    PRODUCTS(10),
    PROGRAMMING(16),
    RELIGION(18),
    SCIENCE(8),
    SHOPPING(34),
    SOCIETY(4),
    SPORTS(27),
    TECHNOLOGY(30),
    TOP_BLOGS(31),
    TOP_NEWS(26),
    TRAVEL(23),
    UNIVERSITIES(12),
    USA(7),
    VIDEO(590),
    VIDEO_GAMES(9),
    WORLD_NEWS(19);
       
    private final int categoryId;
    
    NewsCategory(int categoryId){
        this.categoryId = categoryId;
    }
    
    public int getCategoryId(){
        return categoryId;
    }
    
}
