/*
*
 * Created by jpadjasek on 2015-09-10.
var currentMarker = null;



function handlePointClick(event) {
    if(currentMarker === null) {
        document.getElementById('lat').value = event.latLng.lat();
        document.getElementById('lng').value = event.latLng.lng();

        currentMarker = new google.maps.Marker({
            position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
        });

        PF('geoMap').addOverlay(currentMarker);

        PF('dlg').show();
    }
}

function markerAddComplete() {
    var title = document.getElementById('title');
    currentMarker.setTitle(title.value);
    title.value = "";

    currentMarker = null;
    PF('dlg').hide();
}

function cancel() {
    PF('dlg').hide();
    currentMarker.setMap(null);
    currentMarker = null;

    return false;
}*/
