var SynereoSelectizeFacade = (function(){
    function addOption(selectizeId, text, value) {
        var $select= $("#"+selectizeId)[0].selectize
        $select.addOption({
            text:text,
            value: value
        })
    }
    function initilizeSelectize(selectizeId,maximumItems,allowCreate){
     $("#"+selectizeId).selectize({
        plugins: ['remove_button'],
        create: allowCreate,
        maxItems:maximumItems
    })
 }
 return {
    addOption:addOption,
    initilizeSelectize:initilizeSelectize
}

})();