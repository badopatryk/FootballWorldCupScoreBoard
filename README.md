Hello, so first of all a couple of assumptions and a little walkthrough through my approach, have fun :))

- we only take care of the score, nothing else like time, yellow cards matters,
- do I want to have menu-like functionality to see how the scoreboard behaves in real-time or only tests,
- does summary consist of only finished games? I assume that is does not.

I have two classes:
GameModel:
  -homeTeam
  -awayTeam
  -homeTeamGoals
  -awayTeamGoals
  -timestamp

ScoreBoard:
  -Map<String, GameModel> games
  -startGame()
  -finishGame()
  -updateGame()
  -getSummary()




1. Start a game. When a game starts, it should capture (being initial score 0-0)
a. Home team 
b. Away Team
2. Finish a game. It will remove a match from the scoreboard.
3. Update score. Receiving the pair score; home team score and away team score
updates a game score
4. Get a summary of games by total score. Those games with the same total score
will be returned ordered by the most recently added to our system.

As an example, being the current data in the system:
a. Mexico - Canada: 0 – 5
b. Spain - Brazil: 10 – 2
c. Germany - France: 2 – 2
d. Uruguay - Italy: 6 – 6
e. Argentina - Australia: 3 - 1

The summary would provide with the following information:
1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2
