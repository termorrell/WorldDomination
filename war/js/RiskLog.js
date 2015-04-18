var RiskLog = {

    init: function() {

        for (var i = 0; i < 10; i++) {
            RiskLog.addEntry("Nullam quis risus eget urna mollis ornare vel eu leo. Integer posuere erat a ante venenatis dapibus posuere velit aliquet.");
        }
    },

    addEntry: function(entry) {
        if (entry) {

            var date = new Date();
            var timeStamp = date.toLocaleTimeString();
            var hintLabel = $("#hint .gwt-Label");
            var existingEntry = $("#hint .gwt-Label h5").text();
            if (existingEntry != "") {
                $("#log .text").append("<span>" + timeStamp + " - " + existingEntry + "</span>");
            }
            hintLabel.html("<h5>" + entry + "</h5>");
            $("#log .text").scrollTop($("#log .text")[0].scrollHeight);
        }
    },

    clearLog:function() {
x
    },

    show: function() {

        $("#log").css({
            height: "250px",
            opacity: "1.0"
        });
        $("#controller").css({
            bottom: "370px"
        });
    },

    hide: function () {

        $("#log").css({
            height: "0px",
            opacity: "0.0"
        });
        $("#controller").css({
            bottom: "80px"
        });
    }
}