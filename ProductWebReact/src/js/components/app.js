import React from 'react'
import { render } from 'react-dom'
import Header from './header'
import Footer from './footer'
import MainSection from './mainSection'

const App = React.createClass({
	render() {
		return(
			<div>
				<Header />				
				<MainSection />
				<Footer />
			</div>
			)
	}
})

export default App


// var React = require('react');
// var ReactDOM = require('react-dom');
// var Header = require('./header');
// var MainSection = require('./main_section');
// var Footer = require('./footer');

// var App = React.createClass({
// 	render:function(){
// 		return (
// 			<div>
// 				<Header />				
// 				<MainSection />
// 				<Footer />
// 			</div>
// 			);
// 	}
// });

// module.exports = App;