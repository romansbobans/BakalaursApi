$(document).on('change', '.btn-file :file', function() {
        var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

$(document).ready(function() {

    var panels = [];

    function Panel(lang, title, description) {
        this.lang = lang;
        this.title = title;
        this.description = description;
    }

    /**
     * Triggers when add category pressed
     */
    $("#add-category").click(function() {
        var clonable = $(".panel-wrapper").first().clone(true, true);
        clonable.find("input").val("");
        $("#panels").append(clonable.fadeIn("fast"));
    });

    /**
     * Triggers when submit button pressed
     */
    $("#submit").click(function() {

        $(".panel-wrapper").each(function() {
            var lang = $(this).find(".lang").val();
            var title = $(this).find(".title").val();
            var description = $(this).find(".description").val();

            panels.push(new Panel(lang, title, description));
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

});


