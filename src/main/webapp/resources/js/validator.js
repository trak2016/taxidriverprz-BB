/**
 * Created by akrawczyk on 2015-09-11.
 */
$(document).ready(function (){
    validate();
    $('[id="addCarForm:plateNumber"], [id="addCarForm:brandModel"], [id="addCarForm:capacity"], [id="addCarForm:nbOfSeats"],' +
        ' [id="addCarForm:dob"], [id="addCarForm:company_"] ,[id="addCarForm:addCar"]').change(validate);
});
function validate(){
    if ($('[id="addCarForm:plateNumber"]').val().length   >   0   &&
        $('[id="addCarForm:brandModel"]').val().length  >   0   &&
        $('[id="addCarForm:capacity"]').val().length  >   0   &&
        $('[id="addCarForm:nbOfSeats"]').val().length  >   0   &&
        $('[id="addCarForm:dob_input"]').val() && $('[id="addCarForm:dob_input"]').val().length &&
        $('[id="addCarForm:company_"]').val() && $('[id="addCarForm:company_"]').val().length &&
        $('[id="addCarForm:user_"]').val() && $('[id="addCarForm:user_"]').val().length ) {
        $('[id="addCarForm:addCar"]').prop("disabled", false);
        $('[id="addCarForm:addCar"]').removeClass('ui-state-disabled');
        $('[id="addCarForm:addCar"]').addClass('ui-state-default');
    }
    else {
        $('[id="addCarForm:addCar"]').prop("disabled", true);
        $('[id="addCarForm:addCar"]').removeClass("ui-state-default");
        $('[id="addCarForm:addCar"]').addClass("ui-state-disabled");
    }
}