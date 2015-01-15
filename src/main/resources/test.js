//Table 1

var writedata = function(data){
    var table = "<thead><tr><th>Name</th></tr></thead><tbody>";
    for(var i=0; i<data.length; i++){
        table += "<tr><td>" + data[i]["description"] + "</td></tr>";
    }
    table += "</tbody>";
    AJS.$("#mytable").html(table);
};

var fillselectoptions = function(output){
    var options = "";
    var target_class = ".name-select";
    for (var i = 0; i < output.length; i++) {
        options += '<option value="' + output[i]["description"] + '">' + output[i]["description"] + "</option>"
    }

    AJS.$(target_class).each(function(){
        AJS.$(this).html(options);
        b = new AJS.SingleSelect(this);
    });
};

AJS.$(document).ready(function() {
    AJS.$.ajax({
        url: AJS.params.baseURL + "/plugins/servlet/name/list",
        type: "GET",
        dataType: "json",
        data: ({"projectsOnly" : "true"}),
        success: writedata
    });

    AJS.$.ajax({
        url: AJS.params.baseURL + "/plugins/servlet/entry/list",
        type: "GET",
        dataType: "json",
        data: ({"projectsOnly" : "true"}),
        success: writedata2
    });
});


AJS.$("#myform").submit(function() {
    AJS.$.ajax({
        url: AJS.params.baseURL + "/plugins/servlet/name/list",
        type: "POST",
        dataType: "json",
        data: AJS.$("#myform").serialize(), // serializes the form's elements.
        success: writedata
    });

    AJS.$("#name-input").val('');
    return false; // avoid to execute the actual submit of the form.
});

//Table 2
var writedata2 = function(data){
    var table2 = "<thead><tr><th>Entry</th><th>Name</th></tr></thead><tbody>";
    for(var i=0; i<data.length; i++){
        table2 += "<tr><td>" + data[i]["entry_name"] + "</td><td><form class=\"aui\"><p><select name=\"product\" class=\"name-select\"></select></p></form></td></tr>";
    }
    table2 += "";
    table2 += "</tbody>";
    AJS.$("#mytable2").html(table2);
    var options_url = AJS.params.baseURL + "/plugins/servlet/name/list";
    AJS.$.ajax({
        url: options_url,
        type: "GET",
        dataType: "json",
        data: ({"projectsOnly": "true"}),
        success: fillselectoptions
    });
};


AJS.$("#myform2").submit(function() {
    AJS.$.ajax({
        url: AJS.params.baseURL + "/plugins/servlet/entry/list",
        type: "POST",
        dataType: "json",
        data: AJS.$("#myform2").serialize(), // serializes the form's elements.
        success: writedata2
    });

    AJS.$("#entry-input").val('');
    return false; // avoid to execute the actual submit of the form.
});


