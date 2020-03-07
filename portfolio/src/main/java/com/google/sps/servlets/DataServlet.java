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
  private List<Comment> allComments;

  @Override
  public void init(){
    names = new ArrayList<>();
    names.add("Obi");

    messages = new ArrayList<>();
    messages.add("Hello! How are you");

    allComments = new ArrayList<>();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = names.get(0);

    Query query = new Query("Comment");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String whatSaid = (String) entity.getProperty("Comment");
      String mood = (String) entity.getProperty("Mood");
      //long timestamp = (Long) entity.getProperty("timestamp");
      
      Comment newComment = new Comment(id, whatSaid, mood);
      allComments.add(newComment);
    }
    response.getWriter().println(allComments);


    String json = convertToJsonUsingGson(allComments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String word = request.getParameter("Comment");
      String mood = request.getParameter("Mood");
      
      Entity commentEntity = new Entity("Comment");
      commentEntity.setProperty("Comment", word);
      commentEntity.setProperty("Mood", mood);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);

      response.sendRedirect("/index.html");
  }


  static String convertToJsonUsingGson( List<Comment> words) {
    Gson gson = new Gson();
    String json = gson.toJson(words);
    return json;
  }
}
