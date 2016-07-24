package com.modprobe.profit;


public class QueryMessage {
    
    //private variables
    int _id,_sid;
    String _content;
    int _type, _by;
    // Empty constructor
    public QueryMessage(){
         
    }
    // constructor
    public QueryMessage(int id, String content,  int type, int by,int sid){
        this._id = id;
        this._content = content;
        
        this._type = type;
        this._by = by;
        this._sid = sid;
    }
     
    // constructor
    public QueryMessage(String content, int type, int by){
        this._content = content;
        
        this._type = type;
        this._by = by;
    }
    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        this._content = content;
    }

    public boolean isMine() {
        return (this._by==2)?true:false;
    }

    public void setIsMine() {
        this._by = 2;
    }

    public int type(){
    	return this._type;
    }

    public void setContentType(int type) {
        this._type = type;
    }
     
}