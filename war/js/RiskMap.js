var RiskMap = {

    /**
     * Some other variables
     */
    line: null,
    scale: 1,
    enabled: false,
    moving: false,
    sourceTerritory: null,
    destinationTerritory: null,

    /**
     * Initialise the board and the mouse events
     */
    init: function () {
        //
        //$("#map g#Layer_1 path").on("mousedown", function() {});
        //
        //var map = d3.select("#map svg g#Layer_1")
        //    .on("mousedown", mousedownMap)
        //    .on("mouseup", mouseupMap);


        function mousedownMap() {
            var m = d3.mouse(this);
            map.attr("transform", "translate("+m[0]+","+m[1]+")");

            vis.on("mousemove", mousemoveMap);
        }

        function mousemoveMap() {
            var m = d3.mouse(this);
            map.attr("transform", "translate("+m[0]+","+m[1]+")");
        }

        function mouseupMap() {
            map.on("mousemove", null);
        }
    },

    /**
     * Allow a user to claim a territory
     * @param callback
     */
    claimTerritory: function(callback) {

        //Enable the map
        this.setActive(true);

        $('#map').on('click', '.territory', function () {


            // Pass the result back to the callback
            var territory = Territories[$(this).attr('id')];

            if (territory.player != null) {

                RiskLog.addEntry("Please chose an unclaimed territory");
            } else {

                // Disable further clicks on the territory
                $('#map').off('click');

                // Prevent further user interaction
                RiskMap.setActive(false);

                // Update the board
                RiskMap.updateTerritory(territory, RiskGame.currentPlayer, territory.armies + 1);

                callback(territory.id);
            }
        });
    },

    /**
     *
     * @param callback
     */
    distributeArmy: function(callback) {

        //Enable the map
        this.setActive(true);

        $('#map').on('click', '.territory', function () {

            // Pass the result back to the callback
            var territory = Territories[$(this).attr('id')];

            if (territory.player != RiskGame.currentPlayer) {

                RiskLog.addEntry("Please chose a territory you occupy");
            } else {

                // Disable further clicks on the territory
                $('#map').off('click');

                // Prevent further user interaction
                RiskMap.setActive(false);

                // Update the board, increment the number of armies by one
                RiskMap.updateTerritory(territory, territory.player, territory.armies + 1);

                callback(territory.id);
            }
        });
    },

    /**
     *
     */
    update: function(data) {

        var territoryObjs = $.parseJSON(data);
        console.log(territoryObjs);
        $.each(territoryObjs.Territories, function(idx, territory) {

            var territoryObj = null;
            $.each(Territories, function(key, value) {
                if(value.id == territory.territoryID) {
                    territoryObj = value;
                }
            });
            var player = null;
            player = $.grep(Players, function(val) {
                if (val.id == territory.playerID) return val;
            })[0];
            RiskMap.updateTerritory(territoryObj, player, territory.armies);
        });
    },

    reinforce: function(armies, callback) {

        //Local variables
        var arrayOfTerritories = [];
        var armiesRemaining = armies;

        //Enable the map
        this.setActive(true);

        $('#map').on('click', '.territory', function () {


            // Pass the result back to the callback
            var territory = Territories[$(this).attr('id')];

            if (territory.player != RiskGame.currentPlayer) {

                RiskLog.addEntry("Please chose a territory you occupy");
            } else {
                $(this).attr('class', 'territory selected');

                armiesRemaining--;

                arrayOfTerritories.push(territory.id);

                // Update the board, increment the number of armies by one
                RiskMap.updateTerritory(territory, territory.player, territory.armies + 1);

                if (armiesRemaining == 0) {
                    // Disable further clicks on the territory
                    $('#map').off('click');
                    $('.territory').attr('class', 'territory');

                    // Prevent further user interaction
                    RiskMap.setActive(false);

                    callback(arrayOfTerritories);
                }
            }
        });
    },

    makeTurn: function() {

        //Local variables
        var territoriesRequired = 2;

        //Enable the map
        this.setActive(true);

        $('#map').on('click', '.territory', function () {

            if (territoriesRequired == 2) {
                RiskMap.sourceTerritory = this;
                territoriesRequired --;
            } else if (territoriesRequired == 1) {
                RiskMap.destinationTerritory = this;
                territoriesRequired --;
            } else {
                $(RiskMap.sourceTerritory).attr('class', 'territory');
                RiskMap.sourceTerritory = RiskMap.destinationTerritory;
                RiskMap.destinationTerritory = this;
            }

            $(this).attr('class','territory selected');
        });
    },

    /**
     *
     * @param active
     */
    setActive: function(active) {

        if (active) {

            // Make the map active
            $('#territories').attr('class','active');
        } else {

            // Deactivate the map
            $('#territories').attr('class','');
        }
    },

    updateTerritory: function(territory, player, armies) {

        if (player == null && armies == null) {
            this.setPlayer(territory, player);
            this.setArmies(territory, armies);
            return;
        }

        if (territory.player != null) {
            if (territory.player.id != player.id) {

                this.setPlayer(territory, player);
            }
            if (territory.armies != armies) {

                this.setArmies(territory, armies);
            }
        } else {

            this.setPlayer(territory, player);
            this.setArmies(territory, armies);
        }
    },

    setPlayer: function(territory, player) {

        if (player) {
            territory.player = player;
            $(territory.domID).find('.army').attr('class', 'army swing');
            setTimeout(function () {
                $(territory.domID).find('.army').attr('class', 'army');
            }, 1200);
            $(territory.domID).find('.army path').attr('stroke', player.color);
        }
    },

    setArmies: function(territory, armies) {

        if (armies) {
            territory.armies = armies;
            $(territory.domID).find('.army').attr('class', 'army swing');
            setTimeout(function () {
                $(territory.domID).find('.army').attr('class', 'army');
            }, 1200);
            $(territory.domID).find('.army text').text(territory.armies);
        }
    },

    /**
     *
     * @param delta
     */
    zoomMap: function (delta) {

        this.scale += delta;

        //TODO: Zoom the map
    }
};