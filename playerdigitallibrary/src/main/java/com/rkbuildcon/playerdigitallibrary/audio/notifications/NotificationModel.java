package com.rkbuildcon.playerdigitallibrary.audio.notifications;

import com.rkbuildcon.playerdigitallibrary.model.FilesModel;

import java.util.ArrayList;

public class NotificationModel {
    private int playBtn = -1;
    private int pauseBtn = -1;
    private int nextBtn = -1;
    private int preBtn = -1;
    private int notificationIcon = -1;
    private int notificationLargeIcon = -1;
    private ArrayList<FilesModel> files = new ArrayList<>();
    private FilesModel file = null;

    public int getPlayBtn() {
        return playBtn;
    }

    public void setPlayBtn(int playBtn) {
        this.playBtn = playBtn;
    }

    public int getPauseBtn() {
        return pauseBtn;
    }

    public void setPauseBtn(int pauseBtn) {
        this.pauseBtn = pauseBtn;
    }

    public int getNextBtn() {
        return nextBtn;
    }

    public void setNextBtn(int nextBtn) {
        this.nextBtn = nextBtn;
    }

    public int getPreBtn() {
        return preBtn;
    }

    public void setPreBtn(int preBtn) {
        this.preBtn = preBtn;
    }

    public ArrayList<FilesModel> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FilesModel> files) {
        this.files = files;
    }

    public FilesModel getFile() {
        return file;
    }

    public void setFile(FilesModel file) {
        this.file = file;
    }

    public int getNotificationIcon() {
        return notificationIcon;
    }

    public void setNotificationIcon(int notificationIcon) {
        this.notificationIcon = notificationIcon;
    }

    public int getNotificationLargeIcon() {
        return notificationLargeIcon;
    }

    public void setNotificationLargeIcon(int notificationLargeIcon) {
        this.notificationLargeIcon = notificationLargeIcon;
    }
}
