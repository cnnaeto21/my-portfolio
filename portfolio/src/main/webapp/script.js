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

/**
 * Adds a random greeting to the page.
 */


 
function addRandomGreeting() {
  const greetings =
      ["I'll be the best Lawyer ever - Mike Ross", "It's gonna be legendary - Barney Stinson", 'The North remembers -Ayra Stark', 'The kevlar of knowing the answer - Bobby Axelrod'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function addRandomNameGreeting(){
    console.log("Fetching a random greeting with a name   ");
    fetch("/data").then(response => response.text()).then((name) => {
        document.getElementById('name-container').innerText = name;
    });

}

function postComments() {
  fetch('data').then(response => response.json()).then((comments) => {
    console.log(comments)
    const commentListElement = document.getElementById('comment-container');
    comments.forEach((comment) => {
      commentListElement.appendChild(createCommentElement(comment));
    })
  });
}

/** Creates an element that represents a task, including its delete button. */
function createCommentElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = 'comment';

  const titleElement = document.createElement('span');
  titleElement.innerText = comment.comment;
  commentElement.appendChild(titleElement);
  return commentElement;
}


google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'Player');
  data.addColumn('number', 'All Star Appearences');
        data.addRows([
          ['Kobe', 16],
          ['Lebron', 14],
          ['Luka', 1]
        ]);

  const options = {
    'title': 'All Star Appearences',
    'width':500,
    'height':400
  };

  const chart = new google.visualization.PieChart(
      document.getElementById('chart-container'));
  chart.draw(data, options);
}
