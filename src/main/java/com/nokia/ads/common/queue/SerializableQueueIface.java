package com.nokia.ads.common.queue;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface SerializableQueueIface<T> {

  public void add(T t);

  public void addAll(Collection<T> c);

  public Iterator<T> iterator();

  public int size();

  public void clear();

  public boolean isEmpty();

  public int serializeAll();

  public int serialize(Iterator<T> iterator);

  public int deserialize(File file);

  public int deserializeAll();

  public List<File> listQueueFiles();

}
