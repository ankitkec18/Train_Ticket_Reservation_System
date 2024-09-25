const currentPath = window.location.pathname.split('/').pop();

        function setActiveMenu() {
            const menuItems = {
                "AdminHome.html": "home",
                "adminviewtrainfwd": "viewTrains",
                "adminsearchtrainfwd": "searchTrain",
                "adminviewuserfwd": "viewUser",
                "adminsearchuserfwd": "searchUser",
                "addtrainfwd": "addTrain",
                "cancletrainfwd": "deleteTrain",
                "updatetrain": "updateTrain",
                "adminlogout": "logout"
            };

            const activeMenuId = menuItems[currentPath];
            if (activeMenuId) {
                document.getElementById(activeMenuId).classList.add('active');
            }
			// Set the active menu item or default to 'home'
			    const idToActivate = activeMenuId || "home"; // Default to home if no match
			    document.getElementById(idToActivate).classList.add('active');
        }
		
        setActiveMenu();/**
 * 
 */