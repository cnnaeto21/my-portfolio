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
    console.log("Fetching a randome greeting with a name   ");
    fetch("/data").then(response => response.text()).then((name) => {
        document.getElementById('name-container').innerText = name;
    });

}

