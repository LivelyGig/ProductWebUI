import React from 'react'
import { render } from 'react-dom'

const Header = React.createClass({
	render() {
		return (

			<nav className="navbar navbar-fixed-top navi-background" role="navigation">
			  <div className="container-fluid navi-container-div">
			    <div className="navbar-header">
			      <button type="submit" className="navbar-toggle glyphicon glyphicon-th-list" data-toggle="collapse" data-target="#navi-collapse">
			      <span className="icon-bar"></span>
			      <span className="icon-bar"></span>
			      <span className="icon-bar"></span>                        
			      </button>
			      <a className="navbar-brand" href="#"><img src="images/logo-symbol.png" className="img-responsive navi-logo img-circle" alt="LivelyGig logo"/></a>
			    </div>
			    <div className="collapse navbar-collapse" id="navi-collapse">
			      <ul className="nav navbar-nav">
			        <li>
			          <a href="#">Messages</a>
			        </li>
			        <li>
			          <a href="#">Projects</a>
			        </li>
			        <li>
			          <a href="#">Talent</a>
			        </li>
			        <li>
			          <a href="#">Offerings</a>
			        </li>
			        <li>
			          <a href="#">Contacts</a>
			        </li>
			        <li>
			          <a href="#">Connections</a>
			        </li>
			      </ul>
			      <div className="navbar-right user-profile-div">
			        <ul className="nav navbar-nav user-profile-ul">
			          <li><a href="#"><span className="glyphicon glyphicon-bell navi-bell-notify"></span></a></li>
			          <li><a href="#" className="profile-name">Dale Steyn</a></li>
			          <li><img src="images/profile.jpg" className="img-responsive user-profile-pic img-circle" alt="Profile"/></li>
			        </ul>
			      </div> 
			    </div>
			  </div>
			</nav>

			)
	}
})

export default Header

// var React = require('react');
// var ReactDOM = require('react-dom');

// var Header = React.createClass({
// 	render: function(){
// 		return (		
// 			  <nav className="navbar navbar-fixed-top navi-background" role="navigation">
// 			    <div className="container-fluid navi-header-content">
// 			      <div className="navbar-header">
// 			        <button type="button" className="navbar-toggle navbar-toggle-btn" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
// 			          <span className="sr-only">Toggle navigation</span>
// 			          <span className="icon-bar"></span>
// 			          <span className="icon-bar"></span>
// 			          <span className="icon-bar"></span>
// 			        </button>
// 			        <a href="#">
// 			          <img src="images/logo-full.png" className="img-responsive navi-logo" alt="LivelyGig logo" />
// 			        </a>

// 			      </div>
// 			      <div className="col-md-3 col-md-offset-2 col-sm-5">
// 			        <div className="right-inner-addon">
// 			          <i className="icon-search"></i>
// 			          <input type="search" className="form-control" placeholder="Search" />
// 			        </div>
// 			      </div>
// 			      <div className="col-md-3 col-sm-2">
// 			        <div className="btn-group">
// 			          <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown">
// 			            <span data-bind="label" className="navi-dropdown-span">In Gig</span>&nbsp;<span className="caret"></span>
// 			          </button>
// 			          <ul className="dropdown-menu navi-dropdown-list-value" role="menu">
// 			            <li><a href="#">Dummy value1</a></li>
// 			            <li><a href="#">Dummy value2</a></li>
// 			            <li><a href="#">Dummy value3</a></li>
// 			          </ul>
// 			        </div>
// 			      </div>
// 			        <div className="nav navbar-nav navbar-right navi-sign-up-profile">
// 			            <a href="#">SignUp</a>
// 			        </div>
// 			    </div>

// 			  </nav>

// 		);
// 	}
// });

// module.exports = Header;