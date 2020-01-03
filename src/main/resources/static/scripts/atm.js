function setFocusById(ids) {
    for (let i = 0; i < ids.length; i++) {
        let id = ids[i];
        if ($('#' + id).is(':hidden') == false
            && $('#' + id).is(':disabled') == false) {
            $('#' + id).focus();
            return;
        }
    }
}