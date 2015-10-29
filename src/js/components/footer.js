import React from 'react'
import { render } from 'react-dom'

const Footer = React.createClass({
	render() {
		return (

			    <footer className="footer">
			      <div className="container-fluid foot-container">
			          <div className="row">
			            <div className="col-md-4 col-sm-3 col-xs-12 text-center padding-left-right-0">
			             <ul className="foot-items foot-items-left">
			               <li><a href="#" className="hidden-sm">Follow Us : </a></li>
			               <li><a href="#"><img src="/images/footer_icons/github.png" className="foot-png-img" alt="i"/></a></li>
			               <li><a href="#"><img src="/images/footer_icons/fb.png" className="foot-png-img" alt="i"/></a></li>
			               <li><a href="#"><img src="/images/footer_icons/twit.png" className="foot-png-img" alt="i"/></a></li>
			               <li><a href="#"><img src="/images/footer_icons/slack.png" className="foot-png-img" alt="i"/></a></li>
			             </ul>
			           </div>
			           <div className="col-md-8 col-sm-9 col-xs-12">
			            <ul className="foot-items foot-items-right">
			              <li><a href="#"> About</a></li>
			              <li><a href="#"> Privacy</a></li>
			              <li><a href="#">Terms Of Use</a></li>
			              <li><a href="#">Trademarks</a></li>
			              <li><a href="#"> &copy 2015 <span className="foot-text-logo-red">Lively</span> <span className="foot-text-logo-blue">Gig</span> </a></li>
			            </ul>
			          </div>
			        </div>
			      </div>
				</footer>
		)
	}
})

export default Footer