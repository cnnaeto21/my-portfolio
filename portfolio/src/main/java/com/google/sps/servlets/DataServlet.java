// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private List<String> names;

  private List<String> messages;

  @Override
  public void init(){
    names = new ArrayList<>();
    names.add("Obi");

    messages = new ArrayList<>();
    messages.add("Hello! How are you");
    //messages.add("You got this!");
    //messages.add("Nice to meet you.");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = names.get(0);

    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String whatSaid = (String) entity.getProperty("comment");
      String mood = (String) entity.getProperty("mood");
      long timestamp = (Long) entity.getProperty("timestamp");
      
      Comment comment = new Comment(id, whatSaid, mood, timestamp);
      //messages.add(comment);
    }
    
    String json = convertToJsonUsingGson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String word = request.getParameter("comment");
      String mood = request.getParameter("mood");
      messages.add(word);
      
      Entity commentEntity = new Entity("Comment");
      commentEntity.setProperty("Comment", word);
      commentEntity.setProperty("Mood", mood);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
  }


  static String convertToJsonUsingGson( List<String> words) {
    Gson gson = new Gson();
    String json = gson.toJson(words);
    return json;
  }
}
