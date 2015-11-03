import React from 'react'
import { render } from 'react-dom'

const LeftContainer = React.createClass({
	render() {
		return (

				<div> 
				   <div id="perfect-scroll-container">
				      <h4>Skills</h4>
				      <div  className="ui-widget-content resizable">
				        <select id="tokenize" multiple="multiple" className="tokenize-sample">
				          <option value="1">Dave</option>
				          <option value="2">Paul</option>
				          <option value="3">Michel</option>
				          <option value="4">Anna</option>
				          <option value="5">Eleanor</option>
				        </select>
				      </div>
				      <h4>Categories</h4>
				      <div  className="ui-widget-content resizable">
				        <div id="dynatree"> </div>
				      </div>
				      <h4>Price Range</h4>
				      <form className="form-inline" role="form">
				        <div className="checkbox">
				          <input type="checkbox" className="checkbox-height-width" /> 
				        </div>
				        <div className="form-group">
				          <input type="text" className="form-control input-height-width" id="" />
				        </div>
				      </form>
				      <form className="form-inline" role="form">
				        <div className="checkbox">
				          <input type="checkbox" className="checkbox-height-width" /> 
				        </div>
				        <div className="form-group">
				          <input type="text" className="form-control input-height-width" id="" />
				        </div>
				      </form>
				      <h4>Posted Date</h4>
				      <form className="form-inline" role="form">
				        <div className="checkbox">
				          <input type="checkbox" className="checkbox-height-width" /> 
				        </div>
				        <div className="form-group">
				          <input type="text" className="form-control input-height-width" id="" />
				        </div>
				      </form>
				      <form className="form-inline" role="form">
				        <div className="checkbox">
				          <input type="checkbox" className="checkbox-height-width" /> 
				        </div>
				        <div className="form-group">
				          <input type="text" className="form-control input-height-width" id="" />
				        </div>
				      </form>
				      <h4>Channel</h4>
				      <div  className="ui-widget-content resizable">
				        <select id="tokenizechannel" multiple="multiple" className="tokenize-sample">
				          <option value="1">@Tom</option>
				          <option value="2">@Reza</option>
				          <option value="3">@Michel</option>
				          <option value="4">@Anna</option>
				          <option value="5">@Eleanor</option>
				        </select>
				      </div>
				    </div> 


				 </div>
			)
	}
})

export default LeftContainer