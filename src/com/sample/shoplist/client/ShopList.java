package com.sample.shoplist.client;

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ShopList implements EntryPoint {
    TextBox listItemTB= new TextBox();
    Button addItem= new Button("ADD ITEM");
    Button showAll= new Button("Show All");
    Button random=new Button("Randomly Populate");
    Button showSelected= new Button("Show Selected");
    Button deleteAll=new Button("Delete");
    FlowPanel divList=new FlowPanel();
    FlexTable listH=new FlexTable();
    FlexTable listB=new FlexTable();
    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> done=new ArrayList<String>();
    ScrollPanel scrollPanel = new ScrollPanel();
    boolean showSelectedFlag=false;
     
    

  public void onModuleLoad() {
    listB.setWidth("100%");
    scrollPanel.add(listB);
    scrollPanel.addStyleName("testDiv");
    listH.setText(0,0,"NAME");
    listH.setText(0,1,"SELECTED");
    divList.add(listH);
    divList.add(scrollPanel);
    RootPanel.get("test").add(random);
    RootPanel.get("test").add(listItemTB);
    RootPanel.get("test").add(addItem);
    RootPanel.get("test").add(divList);
    RootPanel.get("test").add(showAll);
    RootPanel.get("test").add(showSelected);
    RootPanel.get("test").add(deleteAll);

   addItem.addClickHandler(new ClickHandler(){
      public void onClick(ClickEvent evt){
        if(listItemTB.getText()!="")
       { addList();    
        listItemTB.setText("");}

      }
   });

   deleteAll.addClickHandler(new ClickHandler(){
    public void onClick(ClickEvent evt){
      listB.removeAllRows();
      name.clear();
      done.clear();

    }
 });

   random.addClickHandler(new ClickHandler(){
      public void onClick(ClickEvent evt){
        String alphabet= "abcdefghijklmnopqrstuvwxyz";
        String s = "";
        for (int j=0;j<50;j++){
          Random random = new Random();
          int randomLen = 1+random.nextInt(5);
          for (int i = 0; i < randomLen; i++) {
              char c = alphabet.charAt(random.nextInt(26));
              s+=c;
          }
          name.add(s);
          done.add("false");
          s="";
        }
        listB.removeAllRows();
        for(int i=0;i<name.size();i++)
          {
            CheckBox cb=new CheckBox();
              listB.setWidget(i,1,cb);
              listB.setText(i,0,name.get(i));
            } 
      }
   });

   showAll.addClickHandler(new ClickHandler(){
    public void onClick(ClickEvent evt){
     updateList();


    }
 });

 showSelected.addClickHandler(new ClickHandler(){
  public void onClick(ClickEvent evt){
    listB.removeAllRows();
    int count=0;
    for(int i=0;i<name.size();i++)
    {
      CheckBox cb=new CheckBox();
      if(done.get(i).equalsIgnoreCase("true"))
      {
        cb.setValue(true);
        listB.setWidget(count,1,cb);
        listB.setText(count++,0,name.get(i));
      }
    }
  }
});

   listItemTB.addKeyDownHandler(new KeyDownHandler(){
      public void onKeyDown(KeyDownEvent evt){
        if(listItemTB.getText()!="")
        if(evt.getNativeKeyCode()==KeyCodes.KEY_ENTER )
          {addList();    
          listItemTB.setText("");}

      }
   });

   listB.addClickHandler(new ClickHandler() { 
    @Override 
    public void onClick(ClickEvent event) {  
         int rowIndex = listB.getCellForEvent(event).getRowIndex();
         setDone(rowIndex);
    } 
}); 

  }
  public void setDone(int i){
    if(done.get(i).equalsIgnoreCase("false"))
      done.set(i,"true");
    else
      done.set(i, "false");
    updateList();
  }
  public void addList()
  {
    if(name.contains(listItemTB.getText()))
      return;
    if(listItemTB.getText()!=""){
      name.add(listItemTB.getText());
      done.add("false");
    }
    listItemTB.setText("");

    //ADDING ELEMENTS TO FLEXTABLE
    for(int i=0;i<name.size();i++)
    {
      CheckBox cb=new CheckBox();
      listB.setWidget(i,1,cb);
      listB.setText(i,0,name.get(i));
    }
    updateList();
  }
  public void updateList(){
    for(int i=0;i<name.size();i++)
    {
      CheckBox cb=new CheckBox();
      boolean b=done.get(i).equalsIgnoreCase("true")?true:false;
      cb.setValue(b);
      listB.setWidget(i,1,cb);
      listB.setText(i,0,name.get(i));
    }
    alert();
  }

  public static native void alert() /*-{
    var x= $doc.getElementsByClassName("testDiv")[0].children[0].children[0].children[1].children;
    var obj=[];
    for(i=0;i<x.length;i++){
      obj[i]={
        name:x[i].textContent
      }
    }    
    localStorage.setItem("text",JSON.stringify(obj));
   
  }-*/;
}
