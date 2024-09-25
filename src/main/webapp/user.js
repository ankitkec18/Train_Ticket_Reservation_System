const userCurrentPath = window.location.pathname.split('/').pop();

function setUserActiveMenu() {
    const userMenuItems = {
        "userhome": "userHome",
        "userviewtrainfwd": "viewTrains",
        "trainbwstnfwd": "trainBetweenStations",
        "bookingdetails": "bookingHistory",
        "fareenqfwd": "fareEnquiry",
        "useravailfwd": "seatAvailability",
        "usersearchtrain": "searchTrain",
        "userprofile": "profile",
        "userlogout": "logout"
    };

    const userActiveMenuId = userMenuItems[userCurrentPath];

	if (userActiveMenuId) {
	                document.getElementById(userActiveMenuId).classList.add('active');
	            }
				// Set the active menu item or default to 'userHome'
				    const userIdToActivate = userActiveMenuId || "userHome"; // Default to userHome if no match
				    document.getElementById(userIdToActivate).classList.add('active');
				}

				setUserActiveMenu();
/**
 * 
 */