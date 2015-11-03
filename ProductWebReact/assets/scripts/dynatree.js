    $(function(){
        // Attach the dynatree widget to an existing <div id="tree"> element
        // and pass the tree options as an argument to the dynatree() function:
        $("#dynatree").dynatree({
            onActivate: function(node) {
                // A DynaTreeNode object is passed to the activation handler
                // Note: we also get this event, if persistence is on, and the page is reloaded.
                //alert("You activated " + node.data.title);
            },
            persist: true,
            checkbox: true,
          
            children: [ // Pass an array of nodes.
            /*    {title: "Item 1"},*/
                {title: "Marketing", isFolder: true,
                    children: [
                        {title: "Sub-item 1"},
                        {title: "Sub-item 2"}
                    ]
                },
                {title: "Programming", isFolder: true,
                    children: [
                        {title: "Assembly"},
                        {title: "High Level language", isFolder: true,
                         children: [
                                    {title: "Swift"},
                                    {title: "Javascript"}
                                   ]
                        }
                    ]
                }
              /*  {title: "Item 3"}*/
            ]
        });
    });

