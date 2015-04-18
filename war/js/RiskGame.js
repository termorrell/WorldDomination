var RiskGame = {

    currentPlayer: null,

    /**
     * Initialise the page for the first game state
     */
    init: function() {

        var assetManager = new AssetManager();

        assetManager.add('joinGameIcon','img/users2.svg');
        assetManager.add('hostGameIcon','img/wireless12.svg');
        assetManager.add('removeIcon','img/arrows_remove.svg');
        assetManager.add('removeIcon','img/trophy60.svg');
        assetManager.add('removeIcon','img/warning38.svg');
        assetManager.add('removeIcon','img/arrow413.png');
        $.each(Territories, function(key, value) {
            assetManager.add('removeIcon','img/territories/' + value.svg);
        });

        assetManager.downloadAll(function() {
            RiskMap.init();
            setNeighbouringTerritories();
            RiskLog.init();
            RiskGame.showContent();
            setTimeout(function() {
                RiskGame.removeLoader();
            }, 280);
        });
    },

    showContent: function() {
        $(".content-container").delay(300).fadeTo(400, 1.0);
        $("#nav-buttons").delay(300).fadeTo(400, 1.0);
    },

    showMap: function() {
        $("#map").removeClass("blur");
        $(".main-content .overlay").fadeOut();
        RiskLog.show();
        this.allowPlayerNameHover();
        this.hideMapActivityIndicator();
        $("#joinGame").fadeOut();
    },

    hideMap: function() {

        $("#map").addClass("blur");
        $(".main-content .overlay").fadeIn();
        RiskLog.hide();
        this.showMapActivityIndicator();
        $("#joinGame").fadeIn();
    },

    allowPlayerNameHover: function() {

        $('.names').on('mouseover', 'tr', function() {

            $(this).css('background','#eee');
            var player = Players[this.rowIndex - 1];
            $.each(Territories, function(key, value) {
                if(value.player === player) {
                    $(value.domID).attr('class', 'territory selected');
                }
            });
        });
        $('.names').on('mouseout', 'tr', function() {

            $(this).css('background','#fff');
            $('.territory').attr('class', 'territory');
        });
    },

    showTurnControls: function() {
        $(".turnControls").fadeIn();
    },

    hideTurnControls: function() {
        $(".turnControls").fadeOut();
    },

    showInitialiseModal: function() {
        $(".initialiseModal").fadeIn();
    },

    hideInitialiseModal: function() {
        $(".initialiseModal").fadeOut();
    },

    showHostAndPlayModal: function() {
        $(".hostAndPlayModal").fadeIn();
    },

    hideHostAndPlayModal: function() {
        $(".hostAndPlayModal").fadeOut();
    },

    removeLoader: function() {
        this.hideInitialActivityIndicator();
    },

    hideNoPlayersText: function() {
        $("p#noPlayersText").fadeOut();
    },

    hideJoinGameButton: function() {
        $("button#joinGame").fadeOut();
    },

    showLeaveGameButton: function() {
        $("button#leaveGame").fadeIn();
    },

    hideContinueButton: function() {
        $("#continue").fadeOut();
    },

    showContinueButton: function() {
        $("#continue").fadeIn();
    },

    showInitialActivityIndicator: function() {
        $("#initialActivityIndicator").fadeIn();
    },

    hideInitialActivityIndicator: function() {
        $("#initialActivityIndicator").fadeOut();
    },

    showMapActivityIndicator: function() {
        $("#mapActivityIndicator").fadeIn();
    },

    hideMapActivityIndicator: function() {
        $("#mapActivityIndicator").fadeOut();
    },

    showLeaveGame: function() {
        $(".leaveGame").fadeIn();
    },

    hideLeaveGame: function() {
        $(".leaveGame").fadeOut();
    },

    showAlert: function(errorMessage) {
        $(".alert").fadeIn();
        $(".alert p").text(errorMessage);
    },

    hideLeaveGame: function() {
        $(".alert").fadeOut(function() {

            $(".alert p").text("");
            location.reload();
        });
    },

    showNoConnection: function() {
        $(".noConnection").fadeIn();
    },

    hideNoConnection: function() {
        $(".noConnection").fadeOut();
    },

    showAutoDismissError: function(text) {
        $(".modal.error").fadeIn();
        $(".modal.error p").text(text);
        setTimeout(function() {
            RiskGame.hideAutoDismissError();
        }, 500);
    },

    hideAutoDismissError: function() {
        $(".modal.error").fadeOut();
    },

    showGameOver: function() {
        $(".gameOver").fadeIn();
    },

    hideGameOver: function() {
        $(".gameOver").fadeOut();
    },

    showAllocateArmies: function() {
        $('#turnNumber').val(1);
        $(".allocateArmies").fadeIn();
    },

    hideAllocateArmies: function() {
        $(".allocateArmies").fadeOut();
    },

    showCompleteTurn: function() {
        $('#turnNumber').val(1);
        $(".completeTurn").fadeIn();
    },

    hideCompleteTurn: function() {
        $(".completeTurn").fadeOut();
    },

    setCardsEnabled: function() {

        var arrayOfSelectedCards = [];

        $('.cards').on('click', function() {
            if (Cards.length > 0) {

                displayCards();
            }
        });

        function displayCards() {

            $('.cards').off('click');
            $('.cards-close .close').fadeIn();
            $('.cards-close .close').on('click', function() {
                minimiseCards();
            });
            $('.cards').addClass('display');
            $('.cards').on('click','.card', function() {

                var selected = false;
                var originalID = $(this).data('territoryID');
                $.each(arrayOfSelectedCards, function(key, dom) {
                    var id = $(dom).data('territoryID');
                    if (id === originalID) {
                        selected = true;
                    }
                });

                if (!selected) {

                    $(this).addClass('selected');
                    arrayOfSelectedCards.push(this);

                    if (arrayOfSelectedCards.length === 3) {
                        $(arrayOfSelectedCards).remove();
                        $.each(arrayOfSelectedCards, function (key, dom) {
                            var id = $(dom).data('territoryID');
                            var selectedCardIndex;
                            $.each(Cards, function (idx, card) {
                                if (id === card.territoryID) {
                                    selectedCardIndex = idx;
                                }
                            });
                            Cards.splice(selectedCardIndex, 1);
                        });
                        //TODO: need to send the cards being traded in
                        arrayOfSelectedCards = [];
                    }
                    if (Cards.length === 0) {
                        minimiseCards();
                    }
                }
            });
        }

        function minimiseCards() {

            $('.cards').off('click');
            $('.cards .card').removeClass('selected');
            $('.cards-close .close').fadeOut();
            $('.cards-close .close').off('click');
            $('.cards').removeClass('display');
            $('.cards').on('click', function() {
                if (Cards.length > 0) {

                    displayCards();
                }
            });
        }
    },

    setTimer: function(timeOut) {

        var date   = new Date(timeOut);
        var now   = new Date();
        var timeRemaining  = date.getTime() - now.getTime();
        $('.timer').fadeIn();
        $('.timer').animate({ width: '100%'}, timeRemaining, function() {
            $('.timer').css({
                display: 'none',
                width: '100%'
            });
        });
    },

    initialiseGame: function() {

        this.hideNoPlayersText();
        this.hideJoinGameButton();
        this.showLeaveGameButton();
    },

    waitForReadyState: function() {

        this.showMapActivityIndicator();
    },

    ready: function() {

        this.hideMapActivityIndicator();
        this.setCardsEnabled();
    },

    joinGame: function() {
        this.initialiseGame(true);
        this.hideInitialiseModal();
        var ip = $('#ipAddressInput').val();
        var port = parseInt($('#portNumberInput').val());
        window.joinGame(ip, port);
    },

    hostGame: function() {
        this.showHostAndPlayModal();
        this.hideInitialiseModal();
    },

    hostGameOnly: function() {
        this.initialiseGame();
        this.hideHostAndPlayModal();
        this.waitForReadyState();
        window.hostGame(false);
    },

    hostAndJoinGame: function() {
        this.initialiseGame(true);
        this.hideHostAndPlayModal();
        window.hostGame(true);
    },

    clearPlayers: function() {
        Players = [];
    },

    addPlayer: function(id, name, publicKey, color) {
        Players.push(new Player(id, name, publicKey, color))
    },

    setMapEnabled: function(enabled) {
        RiskMap.enabled = enabled;
    },

    currentPlayerEvent: function(playerID, isMe) {

        if (isMe) {
            this.setMapEnabled(true);
        } else {
            this.setMapEnabled(false);
        }
        this.currentPlayer = $.grep(Players, function(val) {
            if (val.id === playerID) return val;
        })[0];
    },

    rejectionEvent: function(errorMessage) {

        this.showAlert(errorMessage);
    },
    
    claimTerritoryEvent: function(callback) {

        RiskMap.claimTerritory(callback);
    },
    
    distributeArmyEvent: function(callback) {

        RiskMap.distributeArmy(callback);
    },
    
    mapUpdateEvent: function (data) {

        RiskMap.update(data);
    },
    
    reinforceEvent: function(armies, callback) {

        RiskMap.reinforce(armies, callback);
    },
    
    makeTurnEvent: function (timeOut, callback) {

        //Setup the map to register input
        RiskMap.sourceTerritory = null;
        RiskMap.destinationTerritory = null;
        RiskMap.makeTurn();
        this.showTurnControls();
        this.setTimer(timeOut);

        function finishTurn() {


            // Prevent further user interaction
            RiskMap.setActive(false);
            $('.territory').attr('class','territory');

            // Disable events on the map and buttons
            $('#map').off('click');
            $('button#makeTurn').off('click');
            $('button#finishTurn').off('click');

            RiskGame.hideTurnControls();
        }

        function resetTurn() {
            $('.territory').attr('class','territory');
            $('#map').off('click');
            RiskMap.makeTurn();
        }

        $('button#makeTurn').on('click', function() {

            //Local variables
            var arrayOfTerritories = [];
            if (RiskMap.sourceTerritory) arrayOfTerritories.push($(RiskMap.sourceTerritory).attr('id'));
            if (RiskMap.destinationTerritory) arrayOfTerritories.push($(RiskMap.destinationTerritory).attr('id'));

            //TODO: Need to verify is a neighboring territory

            if (arrayOfTerritories.length === 2) {

                var sourceTerritory = Territories[$(RiskMap.sourceTerritory).attr('id')];
                var destinationTerritory = Territories[$(RiskMap.destinationTerritory).attr('id')];

                var isAttack;
                if (sourceTerritory.player === RiskGame.currentPlayer) {
                    var isNeighbour = false;
                    $.each(sourceTerritory.neighbours, function(idx, value) {
                        if (value.id == destinationTerritory.id) {
                            isNeighbour = true;
                        }
                    });
                    if (isNeighbour) {
                        if (destinationTerritory.player === RiskGame.currentPlayer) {
                            isAttack = false;
                        } else {
                            isAttack = true;
                        }
                    } else {

                        resetTurn();
                        RiskLog.addEntry("You need to choose a neighbouring territory as the source");
                        return;
                    }
                } else {

                    resetTurn();
                    RiskLog.addEntry("You need to choose your own territory as the source");
                    return;
                }

                if (sourceTerritory.armies < 2) {
                    resetTurn();
                    RiskLog.addEntry("You need at least 2 armies in the source territory");
                    return;
                }


                finishTurn();

                var maxNumberOfArmies;
                if (isAttack) {
                    maxNumberOfArmies = sourceTerritory.armies - 1;
                    if (maxNumberOfArmies > 3) maxNumberOfArmies = 3;
                } else {
                    maxNumberOfArmies = sourceTerritory.armies - 1;
                }
                RiskGame.completeTurn(sourceTerritory.id, destinationTerritory.id, maxNumberOfArmies, function(numberOfArmies) {

                    var type = isAttack ? 'Attack' : 'Fortify';
                    callback(type, sourceTerritory.id, destinationTerritory.id, numberOfArmies);
                });
            } else {

                RiskLog.addEntry("Please make a legal move");
            }
        });
        $('button#finishTurn').on('click', function() {

            finishTurn();
            callback("Quit", null, null, null);
        });
    },

    completeTurn: function(sourceTerritory, destinationTerritory, maximumNumberOfArmies, callback) {

        this.showCompleteTurn();

        var sourceImageName, destinationImageName;
        $.each(Territories, function(key, value) {
            if(value.id === sourceTerritory) {
                sourceImageName = value.svg;
            } else if (value.id === destinationTerritory) {
                destinationImageName = value.svg;
            }
        });
        $('.completeTurn img#turnSource').attr('src', 'img/territories/' + sourceImageName);
        $('.completeTurn img#turnDestination').attr('src', 'img/territories/' + destinationImageName);
        $('#turnNumber').attr('max', maximumNumberOfArmies);

        $('#completeTurn').on('click', function () {
            $('#completeTurn').off('click');
            RiskGame.hideCompleteTurn();
            var value = $('#turnNumber').val();
            callback(value);
        });
    },

    allocateCardEvent: function(territoryID, type) {

        var card = new Card(territoryID, type);
        Cards.push(card);

        var imageName, terriotoryName;
        $.each(Territories, function(key, value) {
            if(value.id === territoryID) {
                imageName = value.svg;
                terriotoryName = key + 'Card';
            }
        });

        var returnValue = $('.cards').append('<div class="card"><h5>' + card.type + '</h5><img src="img/territories/' + imageName + '"></div>');
        returnValue.children().last().data('card', terriotoryName);
        returnValue.children().last().data('territoryID', territoryID);
    },

    allocateArmiesEvent: function (sourceTerritory, destinationTerritory, maximumNumberOfArmies, callback) {

        this.showAllocateArmies();

        var sourceImageName, destinationImageName;
        $.each(Territories, function(key, value) {
            if(value.id === sourceTerritory) {
                sourceImageName = value.svg;
            } else if (value.id === destinationTerritory) {
                destinationImageName = value.svg;
            }
        });
        $('.allocateArmies img#allocateSource').attr('src', 'img/territories/' + sourceImageName);
        $('.allocateArmies img#allocateDestination').attr('src', 'img/territories/' + destinationImageName);
        $('#allocateNumer').attr('max', maximumNumberOfArmies);

        $('#allocateArmies').on('click', function () {
            $('#allocateArmies').off('click');
            RiskGame.hideAllocateArmies();
            var value = $('#allocateNumer').val();
            callback(value);
        });
    },
    
    winnerEvent: function (players) {

        $.each(players, function(key, value) {
            $('.players').append('<li>' + value.name_0 + '</li>');
        });

        this.showGameOver();

        //TODO: Need to show the winners
    }
    
};