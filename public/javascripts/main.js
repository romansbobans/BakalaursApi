$(document).on('change', '.btn-file :file', function() {
        var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

$(document).ready(function() {

    var panels = [];

    function Panel(lang, name, shortDescription) {
        this.lang = lang;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = null;
        this.workingHours = null;
        this.groups = null;
    }

    function Day(day, from, to) {
        this.day = day;
        this.hours = from + " - " + to;
    }

    function Group(name, price) {
        this.groupName = name;
        this.groupPrice = price;
    }

    /**
     * Triggers when add category pressed
     */
    $("#add").click(function() {
        var clonable = $(".panel-wrapper").first().clone(true, true);
        clonable.find("input").val("");
        clonable.find(("select"))[0].selectedIndex = 0;
        $("#panels").append(clonable.fadeIn("fast"));
    });

    $("#add-day").click(function() {
        var clonable = $(".days-object").first().clone(true, true);
        clonable.find("input").val("");
        clonable.find("#add-day").remove();
        $(this).closest(".days-wrapper").append(clonable.fadeIn("fast"));
    });

    $("#add-group").click(function(){
        var clonable = $(".groups-wrapper .group").first().clone(true, true);
        console.log(clonable);
        clonable.find("input").val("");
        clonable.find("#add-group").remove();
        $(this).closest(".groups-wrapper").append(clonable.fadeIn("fast"));
    });



    $("#submit-objects").click(function() {

        var workingHours = [];
        var groups = [];
        var location = null;

        $(".latlng").each(function() {
            var lat = $(this).find(".lat").val();
            var lng = $(this).find(".lng").val();
            location = "\"location\": {\"lat\": \"" + lat +"\", \"lng\":\"" + lng + "\"}";

        });
        $(".panel-wrapper").each(function() {

            var lang = $(this).find(".lang").val();
            var name = $(this).find(".name").val();
            var shortDescription = $(this).find(".shortDescription").val();
            var description = $(this).find(".description").val();

            var panel = new Panel(lang, name, shortDescription);
            panel.description = description;

            $(this).find(".days-object").each(function() {
                var day = $(this).find(".day").val();
                var from = $(this).find(".from").val();
                var to = $(this).find(".to").val();
                if (day == "" || from == "" || to == "")
                {
                }
                else
                {
                    workingHours.push(new Day(day, from, to));
                }
            });

            $(this).find(".group").each(function() {
               var name = $(this).find(".group-name").val();
               var price = $(this).find(".group-price").val();

                if (name == "" || price == "")
                {
                }
                else
                {
                    groups.push(new Group(name, price));
                }
            });

            panel.workingHours = workingHours;
            panel.groups = groups;

            panels.push(panel);
        });

        var jsonString = JSON.stringify(panels);

        var total = "{" + location + ", \"objects\":" + jsonString + "}";
        console.log(total);

        $("#panels-json").val(total);
        $("#objects-form").submit();
    });

    /**
     * Triggers when submit for categories button pressed
     */
    $("#submit").click(function() {

        $(".panel-wrapper").each(function() {
            var lang = $(this).find(".lang").val();
            var name = $(this).find(".name").val();
            var shortDescription = $(this).find(".shortDescription").val();

            panels.push(new Panel(lang, name, shortDescription));
        })

        // convert to json and serialize base64
        var jsonString = JSON.stringify(panels);
        var jsonBase64String = $.base64.btoa(jsonString);

        console.log(panels);
        console.log(jsonString);
        console.log(jsonBase64String);

        // insert in hidden input field as value
        $("#panels-json").val(jsonString);

        // submit the form manually
        $("#categories-form").submit();
    });

    /**
     * Triggers when remove block icon clicked
     */
    $("span.remove-button").on('click', function(){
        if ($(".panel-wrapper").length<=1) return;

        $(this).closest(".panel-wrapper").fadeOut("slow", function() {
            $(this).remove()
        });
    });

    /**
     * For file select
     */
    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;

        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }
    });

    /**
     * Timepickers
     */
    $('#timepicker1').timepicker();

});


