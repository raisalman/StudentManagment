package com.example.assignmentalerts.ui.Models;

public class WorkList {
    private String topic;
    private String type;
    private String subject;
    private String deadLine;
    private String senderId;
    private String work_id;

    public WorkList() {}

    //Update/Delete Constructor


    public WorkList(String topic, String subject, String deadLine) {
        this.topic = topic;
        this.subject = subject;
        this.deadLine = deadLine;
    }

    public WorkList(String topic, String subject, String deadLine, String senderId,String type) {
        this.topic = topic;
        this.subject = subject;
        this.deadLine = deadLine;
        this.senderId = senderId;
        this.type = type;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }
}
