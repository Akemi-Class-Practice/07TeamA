	const cart = document.getElementById("cart-icon");
	const cartNumber = document.getElementById("cart-amount");
	
	cart.addEventListener("mouseover", mouseOver);
	cart.addEventListener("mouseout", mouseOut);
	
	function mouseOver() {
		console.log("hover");
		cartNumber.classList.add("cart-amount-hover");
	}
	
	function mouseOut() {
		console.log("mouseout");
		cartNumber.classList.remove("cart-amount-hover");
	}