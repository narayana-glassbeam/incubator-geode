/*=========================================================================
 * Copyright (c) 2010-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * one or more patents listed at http://www.pivotal.io/patents.
 *=========================================================================
 */
package com.gemstone.gemfire.management.internal.cli.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import com.gemstone.gemfire.DataSerializer;
import com.gemstone.gemfire.management.internal.cli.i18n.CliStrings;

/**
 * Domain object used for Data Commands Functions
 * @author tushark
 * 
 * TODO : Implement DataSerializable
 *
 */
public class DataCommandRequest implements /*Data*/ Serializable{

  protected static final boolean DEFAULT_LOAD_ON_CACHE_MISS = false;

  private boolean loadOnCacheMiss = DEFAULT_LOAD_ON_CACHE_MISS;
  private boolean putIfAbsent;
  private boolean recursive;

  private String command;
  private String key;
  private String keyClass;
  private String query;
  private String regionName;
  private String removeAllKeys;
  private String value;
  private String valueClass;

  public static final String NEW_LINE = System.getProperty("line.separator");

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    if(isGet()){
      sb.append(" Type  : Get").append(NEW_LINE);
      sb.append(" Key  : ").append(key).append(NEW_LINE);
      sb.append(" RegionName  : ").append(regionName).append(NEW_LINE);
      sb.append(" LoadOnCacheMiss : ").append(loadOnCacheMiss).append(NEW_LINE);
    }else if(isLocateEntry()){
      sb.append(" Type  : Locate Entry").append(NEW_LINE);
      sb.append(" Key  : ").append(key).append(NEW_LINE);
      sb.append(" RegionName  : ").append(regionName).append(NEW_LINE);
      sb.append(" Recursive  : ").append(recursive).append(NEW_LINE);
    }else if(isPut()){
      sb.append(" Type  : Put");
      sb.append(" Key  : ").append(key).append(NEW_LINE);
      sb.append(" putIfAbsent  : ").append(putIfAbsent).append(NEW_LINE);
      sb.append(" Value  : ").append(value).append(NEW_LINE);
      sb.append(" RegionName  : ").append(regionName).append(NEW_LINE);
    }else if(isRemove()){
      sb.append(" Type  : Remove");
      sb.append(" Key  : ").append(key).append(NEW_LINE);
      sb.append(" removeAllKeys  : ").append(removeAllKeys).append(NEW_LINE);
      sb.append(" RegionName  : ").append(regionName).append(NEW_LINE);
    }else if(isSelect()){
      sb.append(" Type  : SELECT");
      sb.append(" Query  : ").append(query).append(NEW_LINE);
    }
    return sb.toString();
  }

  public boolean isGet(){
    return CliStrings.GET.equals(command);
  }
  
  public boolean isPut(){
    return CliStrings.PUT.equals(command);
  }
  
  public boolean isRemove(){
    return CliStrings.REMOVE.equals(command);
  }
  
  public boolean isLocateEntry(){
    return CliStrings.LOCATE_ENTRY.equals(command);
  }
  
  public boolean isSelect(){
    return CliStrings.QUERY.equals(command);
  }
  
  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getCommand() {
    return command;
  }
  public String getKey() {
    return key;
  }
  public String getValue() {
    return value;
  }
  public boolean isPutIfAbsent() {
    return putIfAbsent;
  }
  public String getKeyClass() {
    return keyClass;
  }
  public String getValueClass() {
    return valueClass;
  }
  public String getRegionName() {
    return regionName;
  }
  public String getRemoveAllKeys() {
    return removeAllKeys;
  }
  public boolean isLoadOnCacheMiss() {
    return loadOnCacheMiss;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setPutIfAbsent(boolean putIfAbsent) {
    this.putIfAbsent = putIfAbsent;
  }

  public void setKeyClass(String keyClass) {
    this.keyClass = keyClass;
  }

  public void setValueClass(String valueClass) {
    this.valueClass = valueClass;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public void setRemoveAllKeys(String removeAllKeys) {
    this.removeAllKeys = removeAllKeys;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public boolean isRecursive() {
    return recursive;
  }

  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }

  public void setLoadOnCacheMiss(final boolean loadOnCacheMiss) {
    this.loadOnCacheMiss = loadOnCacheMiss;
  }

  //@Override
  public void toData(DataOutput out) throws IOException {
    DataSerializer.writeString(command,out);
    DataSerializer.writeString(key,out);
    DataSerializer.writeString(value,out);
    DataSerializer.writeBoolean(putIfAbsent,out);
    DataSerializer.writeString(keyClass,out);
    DataSerializer.writeString(valueClass,out);
    DataSerializer.writeString(regionName,out);
    DataSerializer.writeString(removeAllKeys,out);
    DataSerializer.writeBoolean(recursive,out);    
    DataSerializer.writeBoolean(loadOnCacheMiss,out);
  }

  //@Override
  public void fromData(DataInput in) throws IOException, ClassNotFoundException {    
    command = DataSerializer.readString(in);
    key = DataSerializer.readString(in);
    value = DataSerializer.readString(in);
    putIfAbsent = DataSerializer.readBoolean(in);
    keyClass = DataSerializer.readString(in);
    valueClass = DataSerializer.readString(in);
    regionName = DataSerializer.readString(in);
    removeAllKeys = DataSerializer.readString(in);
    recursive = DataSerializer.readBoolean(in);
    loadOnCacheMiss = DataSerializer.readBoolean(in);
  }  
  
  

}
