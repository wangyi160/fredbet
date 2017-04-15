$(document).ready(function() {
	checkPenalty();
	
	imageCroppingUpload();
});

function checkPenalty() {
	var goalsOne = $('#goalsTeamOne, #teamResultOne').val();
	var goalsTwo = $('#goalsTeamTwo, #teamResultTwo').val();
	if (goalsOne != "" && goalsTwo != "" && goalsOne == goalsTwo) {
		$('#penaltyBox').show();
	} else {
		$('#penaltyBox').hide();
	}
}

function clearTeamNameFields() {
	$('#nameTeamOne').val("");
	$('#nameTeamTwo').val("");
}

function oneCountUp() {
	var val = $('#goalsTeamOne, #teamResultOne').val();
	$('#goalsTeamOne, #teamResultOne').val((val*1)+1); 
	checkPenalty();
}

function oneCountDown() {
	var val = $('#goalsTeamOne, #teamResultOne').val();
	if (val > 0) {
		$('#goalsTeamOne, #teamResultOne').val((val*1)-1);
		checkPenalty();
	}
}

function twoCountUp() {
	var val = $('#goalsTeamTwo, #teamResultTwo').val();
	$('#goalsTeamTwo, #teamResultTwo').val((val*1)+1);
	checkPenalty();
}

function twoCountDown() {
	var val = $('#goalsTeamTwo, #teamResultTwo').val();
	if (val > 0) {
		$('#goalsTeamTwo, #teamResultTwo').val((val*1)-1); 
		checkPenalty();
	}
}

function imageCroppingUpload() {
	var $uploadCrop;

	function readFile(input) {
			if (input.files && input.files[0]) {
            var reader = new FileReader();
            
            reader.onload = function (e) {				
            	$uploadCrop.croppie('bind', {
            		url: e.target.result
            	}).then(function(){
            		console.log('jQuery bind complete');
            	});
            }
            reader.readAsDataURL(input.files[0]);
        }
        else {
	        console.log("Sorry - you're browser doesn't support the FileReader API");
	    }
	}

	$('#upload').on('change', function () { readFile(this); });
	
	$uploadCrop = $('#croppie-container').croppie({
		viewport: {
			width: 150,
			height: 150,			
		},
		enableExif: true,
		enableOrientation: true,
		format: 'jpeg'		
	});
	
	$('#crop-image').on('click', function (ev) {
		$uploadCrop.croppie('result', {
			type: 'canvas',
			size: 'viewport',
			enableExif: true,
			enableOrientation: true,
			format: 'jpeg'
		}).then(function (resp) {
			console.log('response: '+ resp);
			$("#resultImage").attr("src", resp);
			$("#croppedFileBase64").attr("value", resp);
		});
	});
}