![LivelyGig](http://static1.squarespace.com/static/55b995e0e4b04667a1da39a2/t/563b8a93e4b0a7b5800300e2/1450968381054/?format=400w)

[LivelyGig](http://www.livelygig.com/)'s freelance marketplace front end. Please join LivelyGig at [Slack](https://livelygig.slack.com/messages/general/) or [Twitter](https://twitter.com/LivelyGig/) for more information, community updates and the latest development.

## Application Structure
Application is broadly divided into three parts:
* `server` Used as proxy to GloseVal backend server - this will go away once the front end is fully implemented
* `client` Scala.js application (client side, after compliation etc. runs on browser)
* `shared` Shared scala code between the server and the client

## Building
The build process requires several pieces of software to be installed on the host system:

* [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 8.0
* [SBT](http://www.scala-sbt.org/download.html) 0.13.9 - This will download rest of the required softwares i.e. scala etc.
* Git client

To run the application, open a command line interface (CLI) follow the step below (run individual each command): 

    git clone https://github.com/LivelyGig/ProductWebUI.git
    sbt -verbose -Dhttp.port=8080 run

By default Play Server run on port 9000 and URL would be
* http://localhost:9000/ or
* http://localhost:8080/

To edit source code using IntelliJ IDEA editor please follow the instruction in Google Docs [here] (https://docs.google.com/document/d/1VyU5XtWzXugTa7R3odUEa8I1kmj_nVUa7VgrnkDHnQE/edit)

On Mac, you may need to install 'sbt' using [Homebrew](http://brew.sh/):

    brew install sbt

Or you can configure after downloading and unzipping 'sbt' to your executable path by editing '.bash_profile' if using bash shell on unix or linux or mac system

    export PATH=$PATH:<SBT_DOWNLOADED_UNZIPPED_PATH>/sbt/bin

or in Windows system

    set PATH=%PATH%;<SBT_DOWNLOADED_UNZIPPED_PATH>\sbt\bin

