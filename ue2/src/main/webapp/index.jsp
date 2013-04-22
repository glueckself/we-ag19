<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="java.text.SimpleDateFormat, Formel0.Formel0Bean,Formel0.Formel0Game"%>
<jsp:useBean id="gameData" class="Formel0Bean" scope="session" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="de" xml:lang="de">
    <head>
        <title xml:lang="de">Formel 0 - Spielen</title>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
        <link rel="stylesheet" type="text/css" href="styles/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="container">
            <div id="bordercontainer">
                <div id="header">
                    <div id="header_left"><h1 class="accessibility">Formel 0</h1></div>
                    <div id="header_right"></div>
                </div>
                <div id="navigation">
                    <h2 class="accessibility">Navigation</h2>
                    <ul title="Navigation">
                        <li><a id="startNewGame" href="?command=newGame" tabindex="1">Neues Spiel</a></li>
                        <li><a href="?command=logout" tabindex="2">Ausloggen</a></li>
                    </ul>
                </div>
                <div id="main-area">
                    <div class="info">
                        <h2>Spielinformationen</h2>
                        <table summary="Diese Tabelle zeigt Informationen zum aktuellen Spiel">
                            <tr>
                                <th id="leaderLabel" class="label">F&uuml;hrender</th>
                                <td id="leader" class="data"><%= gameData.getLeaderName() %></td>
                            </tr>
                            <tr>
                                <th id="roundLabel" class="label">Runde</th>
                                <td id="round" class="data"><%= gameData.getRound() %></td>
                            </tr>
                            <tr>
                                <th id="timeLabel" class="label">Zeit</th>
                                <td id="time" class="data"><%= new SimpleDateFormat("mm:ss").format(gameData.getGameDuration()) %></td>
                            </tr>
                            <tr>
                                <th id="computerScoreLabel" class="label">W&uuml;rfelergebnis <em><%= gameData.getComputerPlayer().getName() %></em></th>
                                <td id="computerScore" class="data"><%= gameData.getComputerPlayer().getLastDiceNum() %></td>
                            </tr>
                        </table>
                        <h2>Spieler</h2>
                        <table summary="Diese Tabelle listet die Namen der Spieler auf">
                            <% for(int i=0; i<gameData.getPlayers().size(); i++) { %>
                                <tr>
                                    <% String playerNum = Integer.toString(i+1); %>
                                    <th id="player<%= playerNum %>NameLabel" class="label">Spieler <%= playerNum %></th>
                                    <td id="player<%= playerNum %>Name" class="data"><%= gameData.getPlayer(i).getName() %></td>
                                </tr>
                            <% } %>
                        </table>
                    </div>
                    <div class="field">
                        <h2 class="accessibility">Spielbereich</h2>
                        <ol id="road">
                            <%
                                String liClass;
                                int numOils=0;
                                int posPlayer1 = gameData.getPlayer(0).getPos();
                                int posPlayer2 = gameData.getPlayer(1).getPos();

                                for(int i=0; i<Formel0Game.NUM_FIELDS; i++) {
                                    boolean isOilRoad = false;
                                    liClass="empty_road";
                                    if(numOils < Formel0Game.oilSpits.length) {
                                        if(Formel0Game.oilSpits[numOils] == i) {
                                            liClass="oil_road";
                                            isOilRoad = true;
                                            numOils++;
                                        }
                                    }

                                    switch(i) {
                                        case 0:
                                            out.println("<li id=\"start_road\"> <span class=\"accessibility\">Startfeld</span>");
                                            break;

                                        case Formel0Game.NUM_FIELDS-1:
                                            out.println("<li id=\"finish_road\"><span class=\"accessibility\">Zielfeld</span>");
                                            break;

                                        default:
                                            out.println("<li class=\""+liClass+"\" id=\"road_"+Integer.toString(i+1)+"\">"+
                                                        "<span class=\"accessibility\">Feld "+Integer.toString(i+1)+(isOilRoad ? " mit &Ouml;lfleck" : "")+"</span>");
                                            break;
                                    }

                                    // TODO: Oelfleck in Accessibility-Tag (?)

                                    if(i == posPlayer1) {
                                        out.println("<span id=\"player1\"><span class=\"accessibility\"><em>Spieler 1</em></span></span>");

                                    }

                                    if(i == posPlayer2) {
                                        out.println("<span id=\"player2\"><span class=\"accessibility\"><em>Spieler 2</em></span></span>");

                                    }

                                    out.println("</li>");

                                }
                            %>
                        </ol>
                    </div>
                    <div class="player">
                        <h2 class="accessibility">W&uuml;rfelbereich</h2>
                        <% if(!gameData.isGameFinished()) { %>
                            <span class="accessibility">An der Reihe ist</span>
                        <% } %>
                        <div id="currentPlayerName"><%= gameData.getUserPlayer().getName() %></div>
                        <% if(!gameData.isGameFinished()) { %>
                            <a id="dice" href="?command=dice" tabindex="4">
                        <% } %>

                        <% int diceNum = gameData.getUserPlayer().getLastDiceNum(); %>
                        <img id="diceImage" src="img/wuerfel<%= diceNum %>.png" alt="W&uuml;rfel mit einer <%= diceNum %>" />

                        <% if(!gameData.isGameFinished()) { %>
                            </a>
                        <% } %>
                    </div>
                </div>
            </div>
            <div id="footer">
                &copy; 2013 Formel 0
            </div>
        </div>

        <script type="text/javascript">
            //<![CDATA[

            // call this function once before starting the animations
            function prepareAnimation() {
                $("#animationDone").remove();
            }

            // call this function once after all animations have finished
            function completeAnimation() {
                var div = $(document.createElement('div'));
                div.attr('id', 'animationDone');
                div.addClass('hide');
                $("body").append(div);
            }

            function runAnimation(moves, on_finished) {
                move = moves.shift();

                if (!move) {
                    on_finished();
                    return;
                }

                $(move.id).fadeOut(700, function() {
                    $("ol#road li").eq(move.target).append($(move.id));
                    $(move.id).fadeIn(700, function() {
                        runAnimation(moves, on_finished);
                    });
                });
            }

            $(function() {
                var players = [
                    <% for (int i=0; i < gameData.getPlayers().size(); i++) { %>
                        <% Formel0Bean.Player p = gameData.getPlayer(i); %>
                        {
                            id: "#player<%= i+1 %>",
                            pos: <%= p.getPos() %>,
                            prevPos: <%= p.getPrevPos() %>,
                            diceNum: <%= p.getLastDiceNum() %>
                        }<% if (i+1 < gameData.getPlayers().size()) { out.print(","); } %>
                    <% } %>
                ];

                var moves = [];
                players.forEach(function(p) {
                    // p.prevPos == -1 at game start; don't animate that
                    if (p.prevPos >= 0) {
                        // Move car to previous position
                        $("ol#road li").eq(p.prevPos).append($(p.id));
                        // Animate to prevPos + dice
                        moves.push({id: p.id, target: Math.min(p.prevPos + p.diceNum, <%= Formel0Game.NUM_FIELDS - 1 %>)});
                        // Did we land on an oil spit? Then one more animation is needed
                        if (p.pos == 0) {
                            moves.push({id: p.id, target: 0});
                        }
                    }
                });

                if (moves.length > 0) {
                    prepareAnimation();
                    runAnimation(moves, completeAnimation);
                }
            });

            //]]>
        </script>

    </body>
</html>