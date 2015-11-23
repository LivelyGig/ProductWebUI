    $(function(){
        // Attach the dynatree widget to an existing <div id="tree"> element
        // and pass the tree options as an argument to the dynatree() function:
    
      $("#sclt-dynatree").dynatree({
      checkbox: true,
      selectMode: 3,

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
            ] ,
      onSelect: function(select, node) {
        // Get a list of all selected nodes, and convert to a key array:
        var selKeys = $.map(node.tree.getSelectedNodes(), function(node){
          return node.data.key;
        });
        $("#echoSelection3").text(selKeys.join(", "));

        // Get a list of all selected TOP nodes
        var selRootNodes = node.tree.getSelectedNodes(true);
        // ... and convert to a key array:
        var selRootKeys = $.map(selRootNodes, function(node){
          return node.data.key;
        });
        $("#echoSelectionRootKeys3").text(selRootKeys.join(", "));
        $("#echoSelectionRoots3").text(selRootNodes.join(", "));
      },
      onDblClick: function(node, event) {
        node.toggleSelect();
      },
      onKeydown: function(node, event) {
        if( event.which == 32 ) {
          node.toggleSelect();
          return false;
        }
      },
      // The following options are only required, if we have more than one tree on one page:
//        initId: "treeData",
      cookieId: "dynatree-Cb3",
      idPrefix: "dynatree-Cb3-"
    });



    });

