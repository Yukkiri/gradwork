package ru.puchkova.gradwork;

public class Settings {
    private int theme;
    private int language;

    public Settings(int theme, int language){
        this.theme = theme;
        this.language = language;
    }

    public void setTheme(int theme){
        this.theme = theme;
    }

    public void setLanguage(int language){
        this.language = language;
    }

    public int getTheme(){
        return theme;
    }

    public int getLanguage(){
        return language;
    }

}
