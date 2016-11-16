//custom plugin to provide max length of 16 chars to create tag
Selectize.define('inputMaxlength', function(options) {
  var self = this;
  this.setup = (function() {
    var original = self.setup;
    return function() {
      original.apply(this, arguments);
      this.$control_input.attr('maxlength', this.settings.inputMaxlength);
    };
  })();
});

var SynereoSelectizeFacade = (function(){
    function addOption(selectizeId, text, value) {
        var $select= $("#"+selectizeId)[0].selectize
        $select.addOption({
            text:text,
            value: value
        })
    }
    function initilizeSelectize(selectizeId,maximumItems,maxCharLimit,allowCreate){
     $("#"+selectizeId).selectize({
        plugins: ['remove_button','inputMaxlength'],
        create: allowCreate,
        maxItems:maximumItems,
        inputMaxlength: maxCharLimit,
        closeAfterSelect: true,
        openOnFocus: true
    })
 }
 return {
    addOption:addOption,
    initilizeSelectize:initilizeSelectize
}

})();