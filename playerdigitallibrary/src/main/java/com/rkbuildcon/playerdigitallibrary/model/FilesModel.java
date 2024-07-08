package com.rkbuildcon.playerdigitallibrary.model;

import java.io.File;

public class FilesModel {
    private File file;
    private String name;
    private String artistName;
    private boolean isAudio;
    private String link;
    private String thumbnail;
    public FilesModel(File file, boolean isAudio) {
        this.file = file;
        this.isAudio = isAudio;
    }

    public FilesModel(String link, boolean isAudio){
        this.link = link;
        this.isAudio = isAudio;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAudio() {
        return isAudio;
    }

    public void setAudio(boolean audio) {
        isAudio = audio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
