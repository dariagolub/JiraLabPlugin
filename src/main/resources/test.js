var writedata = function(data){
    var table = "<thead><tr><th>Entry name</th></tr></thead><tbody>";
    for(var i=0; i<data.length; i++){
        table += "<tr><td>" + data[i]["description"] + "</td></tr>";
    }
    table += "</tbody>";
    AJS.$("#mytable").html(table);
};

AJS.$.ajax({
    url: "/jira/plugins/servlet/todo/list",
    type: "GET",
    dataType: "json",
    data: ({"projectsOnly" : "true"}),
    success: writedata
});

AJS.$("#myform").submit(function() {

    AJS.$.ajax({
        url: "/jira/plugins/servlet/todo/list",
        type: "POST",
        dataType: "json",
        data: AJS.$("#myform").serialize(), // serializes the form's elements.
        success: writedata
    });

    AJS.$("#task-input").val('');
    return false; // avoid to execute the actual submit of the form.
});