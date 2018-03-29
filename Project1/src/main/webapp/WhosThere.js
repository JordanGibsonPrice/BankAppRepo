"use strict";

// AJAX request call for cases
function callToTable2(){
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'AppManipulationClearance');
	xhr.onload = () => {
		if (xhr.status === 200) {
			console.log(xhr.responseText);
			var tableData = JSON.parse(xhr.responseText);
			var count = 0;
			for(var data in tableData){
			
				var tableRow = document.createElement('tr');
				var tableData1 = document.createElement('td'); //for attributes in an objects
				var tableData2 = document.createElement('td');
				var tableData3 = document.createElement('td');
				var tableData4 = document.createElement('td');
				var tableData5 = document.createElement('td');
				var tableData6 = document.createElement('td');
				var tableData7 = document.createElement('td');
				var tableData8 = document.createElement('td');
				var tableData9 = document.createElement('td');
				var tableData10 = document.createElement('td');
				var tableData11 = document.createElement('td');
				var tableData12 = document.createElement('td');
				var tableData13 = document.createElement('td');
				var tableData14 = document.createElement('td');
				var tableData15 = document.createElement('td');
				var tableData16 = document.createElement('td');
				var tableData17 = document.createElement('td');
//				var tableData18 = document.createElement('td');
			
				var td1 = document.createTextNode(tableData[data].empId);
				var td2 = document.createTextNode(tableData[data].accNum);
				var td3 = document.createTextNode(tableData[data].appNum);
				var td4 = document.createTextNode(tableData[data].deptNum);
				var td5 = document.createTextNode(tableData[data].empFirst);
				var td6 = document.createTextNode(tableData[data].empLast);
				var td7 = document.createTextNode(tableData[data].email);
				var td8 = document.createTextNode(tableData[data].description);
				var td9 = document.createTextNode("$"+tableData[data].expense.toFixed(2));
				if(tableData[data].justification == "approved"){
					var td10 = document.createTextNode("Congratulations, application "+tableData[data].appNum+" has been approved.");
				}
				else{
					var td10 = document.createTextNode("Undecided");
				}	
				var td11 = document.createTextNode(tableData[data].eventDate);
				var td12 = document.createTextNode(tableData[data].requestTime);
				var td13 = document.createTextNode(tableData[data].grade);	
				var td14 = document.createTextNode(tableData[data].eventLocus);
				var td15 = document.createTextNode(tableData[data].requestType);
				var td16 = document.createTextNode(tableData[data].eventType);
				var td17 = document.createTextNode(tableData[data].standing);
//				var td18 = tableData18.innerHTML="<button data-toggle='collapse' data-target='#accordion"+ count +"' class='btn btn-info btn-xs'>View Detail</button>"
			
				tableData1.appendChild(td1);
				tableData2.appendChild(td2);
				tableData3.appendChild(td3);
				tableData4.appendChild(td4);
				tableData5.appendChild(td5);
				tableData6.appendChild(td6);
				tableData7.appendChild(td7);
				tableData8.appendChild(td8);
				tableData9.appendChild(td9);
				tableData10.appendChild(td10);
				tableData11.appendChild(td11);
				tableData12.appendChild(td12);
				tableData13.appendChild(td13);
				tableData14.appendChild(td14);
				tableData15.appendChild(td15);
				tableData16.appendChild(td16);
				tableData17.appendChild(td17);
//				tableData18.appendChild(td18);
	
				tableRow.appendChild(tableData1);
				tableRow.appendChild(tableData2);
				tableRow.appendChild(tableData3);
				tableRow.appendChild(tableData4);
				tableRow.appendChild(tableData5);
				tableRow.appendChild(tableData6);
				tableRow.appendChild(tableData7);
				tableRow.appendChild(tableData8);
				tableRow.appendChild(tableData9);
				tableRow.appendChild(tableData10);
				tableRow.appendChild(tableData11);
				tableRow.appendChild(tableData12);
				tableRow.appendChild(tableData13);
				tableRow.appendChild(tableData14);
				tableRow.appendChild(tableData15);
				tableRow.appendChild(tableData16);
				tableRow.appendChild(tableData17);
//				tableRow.appendChild(tableData18);
				count++;
				
			}
			
		} else {
			console.log('Request failed.  Returned status of ' + xhr.status);
		}
	};
	xhr.send();	
}