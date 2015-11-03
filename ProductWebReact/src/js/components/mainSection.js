import React from 'react'
import { render } from 'react-dom'
import LeftContainer from './leftContainer'
import RightContainer from './rightContainer'

const MainSection = React.createClass({
	render() {
		return (
				<div>


				<div className="container-fluid middel-navi-container">
				      <div className="row">
				      <div className="col-md-3 col-sm-5 col-xs-6">
				      <div className="dropdown project-create-dropdown">
				          <button className="btn dropdown-toggle project-create-btn" type="button" data-toggle="dropdown">Recommended Matches
				            <span className="caret"></span></button>
				            <ul className="dropdown-menu">
				              <li><a href="#">By Bid Amount</a></li>
				              <li><a href="#">(More Sorting)</a></li>
				              <li><a href="#">profile3</a></li>
				            </ul>
				          </div>
				        </div>
				      <div className="col-md-4 col-sm-5 col-xs-6">
				       <button className="btn create-new-project-btn">Create New Project</button>
				      </div>
				    </div>
				    </div>


				    <div id="main-container" className="container-fluid main-container-div">

				    <div className="split-container">
				    <div className="split">
				      <div  id="slct-scroll-container"> 
				         <div>
				            <h4>Skills</h4>
				            <div  className="slct-left-content-div resizable">
				              <select id="tokenize" multiple="multiple" className="tokenize-sample">
				                <option value="1">Dave</option>
				                <option value="2">Paul</option>
				                <option value="3">Michel</option>
				                <option value="4">Anna</option>
				                <option value="5">Eleanor</option>
				              </select>
				            </div>
				            <h4>Categories</h4>
				            <div  className="slct-left-content-div resizable">
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
				            <div  className="slct-left-content-div resizable">
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
				      <div> 

				       <div className="col-md-12 col-sm-12 col-xs-12 gig-actions-container">
				            <div className="col-md-2 col-sm-4 col-xs-6 text-center">
				              <div className="dropdown gig-actions-dropdown">
				                <button className="btn dropdown-toggle gig-match-btn" type="button" data-toggle="dropdown">LivelyGig Match
				                  <span className="caret"></span></button>
				                  <ul className="dropdown-menu">
				                    <li><a href="#">By Bid Amount</a></li>
				                    <li><a href="#">(More Sorting)</a></li>
				                    <li><a href="#">profile3</a></li>
				                  </ul>
				                </div>
				              </div>
				              <div className="col-md-3 col-sm-5 col-xs-6 pull-right">
				                <div className="pull-right gig-conv-action-btn-container">
				                  <button type="button" id="view-names"className="btn">
				                    <span className="glyphicon glyphicon-minus"></span>
				                  </button>
				                  <button type="button" id="view-details" className="btn">
				                    <span className="glyphicon glyphicon-list"></span>
				                  </button>
				                  <button type="button" id="view-description" className="btn">
				                    <span className="glyphicon glyphicon-align-justify"></span>
				                  </button>
				                </div>
				              </div>
				            </div>
				           <div className="container-fluid gig-conversation">
				            <div id="comment-section-container" className="col-md-12 col-sm-12 col-xs-12 padding-left-right-0">
				              <ul className="media-list">
				                <li className="media profile-description">
				                  <div className="col-md-12 profile-name-holder">
				                    Name:job-title
				                  </div>
				                  <div className="col-md-12">
				                    <div className="Details-holder">
				                      Experience: 6 years
				                    </div>
				                    <div className="Details-holder">
				                      Projects Completed: 15
				                    </div>
				                    <div className="Details-holder">
				                      Availability: Negotiable
				                    </div>
				                  </div>
				                  <div className="media-left">
				                    <a href="#">
				                      <img className="media-object profile-pic" src="images/profile-img.png" alt="i"/>
				                    </a>
				                    <div>
				                      <button type="button" className="btn send-invite-btn">Send Invitation</button>
				                    </div>
				                  </div>
				                  <div className="media-body ">
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
				                    <div className="col-md-12 col-sm-12 col-xs-12 padding-left-right-0">
				                      <div className="col-md-6 col-sm-12 col-xs-12 pull-right profile-action-buttons padding-left-right-0">
				                        <button className="btn">Button1</button>
				                        <button className="btn">Button2</button>
				                        <button className="btn">Button3</button>
				                      </div>
				                    </div>
				                  </div>
				                </li>
				                <li className="media profile-description">
				                  <div className="col-md-12 profile-name-holder">
				                    Name:job-title
				                  </div>
				                  <div className="col-md-12">
				                    <div className="Details-holder">
				                      Experience: 2 years
				                    </div>
				                    <div className="Details-holder">
				                      Projects Completed: 8
				                    </div>
				                    <div className="Details-holder">
				                      Availability: Negotiable
				                    </div>

				                  </div>
				                  <div className="media-left">
				                    <a href="#">
				                      <img className="media-object profile-pic" src="images/profile-img.png" alt="i"/>
				                    </a>
				                    <div>
				                      <button type="button" className="btn send-invite-btn">Send Invitation</button>
				                    </div>
				                  </div>
				                  <div className="media-body ">
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
				                    <div className="col-md-12 col-sm-12 col-xs-12 padding-left-right-0">
				                      <div className="col-md-6 col-sm-12 col-xs-12 pull-right profile-action-buttons padding-left-right-0">
				                        <button className="btn">Button1</button>
				                        <button className="btn">Button2</button>
				                        <button className="btn">Button3</button>
				                      </div>
				                    </div>
				                  </div>
				                </li>
				                <li className="media profile-description">
				                  <div className="col-md-12 profile-name-holder">
				                    Name:job-title
				                  </div>
				                  <div className="col-md-12">
				                    <div className="Details-holder">
				                      Experience: 8 years
				                    </div>
				                    <div className="Details-holder">
				                      Projects Completed: 24
				                    </div>
				                    <div className="Details-holder">
				                      Availability: Negotiable
				                    </div>

				                  </div>
				                  <div className="media-left">
				                    <a href="#">
				                      <img className="media-object profile-pic" src="images/profile-img.png" alt="i"/>
				                    </a>
				                    <div>
				                      <button type="button" className="btn send-invite-btn">Send Invitation</button>
				                    </div>
				                  </div>
				                  <div className="media-body ">
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
				                    <div className="col-md-12 col-sm-12 col-xs-12 padding-left-right-0">
				                      <div className="col-md-6 col-sm-12 col-xs-12 pull-right profile-action-buttons padding-left-right-0">
				                        <button className="btn">Button1</button>
				                        <button className="btn">Button2</button>
				                        <button className="btn">Button3</button>
				                      </div>
				                    </div>
				                  </div>
				                </li>
				                <li className="media profile-description">
				                  <div className="col-md-12 profile-name-holder">
				                    Name:job-title
				                  </div>
				                  <div className="col-md-12">
				                    <div className="Details-holder">
				                      Experience: 7 years
				                    </div>
				                    <div className="Details-holder">
				                      Projects Completed: 18
				                    </div>
				                    <div className="Details-holder">
				                      Availability: Not Availabe
				                    </div>

				                  </div>
				                  <div className="media-left">
				                    <a href="#">
				                      <img className="media-object profile-pic" src="images/profile-img.png" alt="i"/>
				                    </a>
				                    <div>
				                      <button type="button" className="btn send-invite-btn">Send Invitation</button>
				                    </div>
				                  </div>
				                  <div className="media-body ">
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
				                    <div className="col-md-12 col-sm-12 col-xs-12 padding-left-right-0">
				                      <div className="col-md-6 col-sm-12 col-xs-12 pull-right profile-action-buttons padding-left-right-0">
				                        <button className="btn">Button1</button>
				                        <button className="btn">Button2</button>
				                        <button className="btn">Button3</button>
				                      </div>
				                    </div>
				                  </div>
				                </li>
				                <li className="media profile-description">
				                  <div className="col-md-12 profile-name-holder">
				                    Name:job-title
				                  </div>
				                  <div className="col-md-12">
				                    <div className="Details-holder">
				                      Experience: 12 years
				                    </div>
				                    <div className="Details-holder">
				                      Projects Completed: 42
				                    </div>
				                    <div className="Details-holder">
				                      Availability: Negotiable
				                    </div>
				                  </div>
				                  <div className="media-left">
				                    <a href="#">
				                      <img className="media-object profile-pic" src="images/profile-img.png" alt="i"/>
				                    </a>
				                  </div>
				                  <div className="media-body">
				                    lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				                    <div className="col-md-12">
				                      <div className="col-md-6 pull-right profile-action-buttons">
				                        <button className="btn">Button1</button>
				                        <button className="btn">Button2</button>
				                        <button className="btn">Button3</button>
				                      </div>
				                    </div>
				                  </div>
				                </li>
				              </ul>
				            </div>
				          </div> 
				 
				      </div> 
				    </div>
				  </div>
				  
				   
				  </div> 



				
				</div>
			)
	}
})

export default MainSection