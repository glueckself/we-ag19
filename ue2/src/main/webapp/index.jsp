<%@page import="java.text.SimpleDateFormat, Formel0.Formel0Bean,Formel0.Formel0Game"%>
<%
    final int computerPlayer = 1;
    final int userPlayer = 0;
%>

<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
                    <%
                        Formel0Bean gameData;
                        gameData = (Formel0Bean)request.getAttribute("gameData");
                        if(gameData == null) {
                            out.print("big error");
                            //TODO: handle error;
                        }
                     %> 
                    <div class="info">
                        <h2>Spielinformationen</h2>
                        <table summary="Diese Tabelle zeigt Informationen zum aktuellen Spiel">
                            <tr><th id="leaderLabel" class="label">F&uuml;hrender</th><td id="leader" class="data">
                                    <%
                                        int bestPlayer=0;
                                        int currentMax=0;
                                        for(int i = 0; i < Formel0Game.NUM_PLAYERS; i++) {
                                            if(gameData.getPlayerPos(i) > currentMax) {
                                                bestPlayer=i;
                                            }
                                        }
                                        out.print(gameData.getPlayerName(bestPlayer));
                                    %>
                                </td></tr>
                                    <tr><th id="roundLabel" class="label">Runde</th><td id="round" class="data"><% out.print(Integer.toString(gameData.getRound())); %></td></tr>
                            <tr>
                                <th id="timeLabel" class="label">Zeit</th>
                                <td id="time" class="data"><% out.print(new SimpleDateFormat("mm:ss").format(gameData.getGameDuration())); %></td>
                            </tr>
                            <tr>
                                <th id="computerScoreLabel" class="label">W&uuml;rfelergebnis <em><% out.print(gameData.getPlayerName(computerPlayer)); %></em></th>
                                <td id="computerScore" class="data"><% out.print(Integer.toString(gameData.getLastDiceNum(computerPlayer))); %></td>
                            </tr>
                        </table>  
                        <h2>Spieler</h2>
                        <table summary="Diese Tabelle listet die Namen der Spieler auf">
                            <%
                                for(int i=0; i<Formel0Game.NUM_PLAYERS; i++) {
                                    out.print("<tr>");
                                    out.print("<th id=\"player" + Integer.toString(i+1) + "NameLabel\" class=\"label\">Spieler " + Integer.toString(i+1) + "</th>");
                                    out.print("<td id=\"player" + Integer.toString(i+1) + "Name\" class=\"data\">"+ gameData.getPlayerName(i) +"</td>");
                                    out.print("</tr>");
                                }
                            %>
                        </table>    	  
                    </div>
                    <div class="field">
                        <h2 class="accessibility">Spielbereich</h2>
                        <ol id="road">
                            <%      
                                String liClass;
                                int numOils=0;
                                int posPlayer1 = gameData.getPlayerPos(0);
                                int posPlayer2 = gameData.getPlayerPos(1);
                                for(int i=0; i<Formel0Game.NUM_FIELDS; i++) {
                                    liClass="empty_road";
                                    if(numOils < Formel0Game.oilSpits.length) {
                                        if(Formel0Game.oilSpits[numOils] == i) {
                                            liClass="oil_road";
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
                                            out.println("<li class=\""+liClass+"\" id=\"road_"+Integer.toString(i+1)+"\"><span class=\"accessibility\">Feld "+Integer.toString(i+1) +"</span>");
                                            break;
                                    }
                                    
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
                        <span class="accessibility">An der Reihe ist</span><div id="currentPlayerName"> <% out.print(gameData.getPlayerName(userPlayer)); %></div>
                        <%
                        if(!gameData.isGameFinished()) {
                            out.print("<a id=\"dice\" href=\"?command=dice\" tabindex=\"4\">");
                        }
                        int diceNum = gameData.getLastDiceNum(userPlayer);
                        if(diceNum == -1)
                            diceNum=1;
                        
                        out.print("<img id=\"diceImage\" src=\"img/wuerfel"+ Integer.toString(diceNum) +".png\" alt=\"W&uuml;rfel mit einer "+ Integer.toString(diceNum)+"\" />");
                        
                            if(!gameData.isGameFinished()) {
                                out.print("</a>");
                            }
                        %>
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
            
            $("#dice").click(function() {
                prepareAnimation();
                $("#player1").fadeOut(700, function() {
                    $("#player1").appendTo("#start_road");
                    $("#player1").fadeIn(700,completeAnimation);                    
                });
                return false;
            });
            //]]>
        </script>

    </body>
</html>