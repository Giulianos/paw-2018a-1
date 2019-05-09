# Gumpu backend development environment setup

This document describes the tools and steps required to develop Gumpu API.

## Tools
- IDE: IntelliJ IDEA Ultimate 2019.1
- Development server: Apache Tomcat 7.0.76
- Database: PostgreSQL 9.6.12
- Java: JDK 8

## Configuration

### Install Apache Tomcat

  1. Download `apache-tomcat-7.0.76.zip` from [https://archive.apache.org/dist/tomcat/tomcat-7/v7.0.76/bin/](https://archive.apache.org/dist/tomcat/tomcat-7/v7.0.76/bin/)
  2. Extract Apache Tomcat to any folder.

###  Install PostgreSQL
  
  1. On Debian you can run `sudo apt install postgresql`

### Configure database
  
  1. Run `sudo -u postgres psql`
  2. Create user **paw-2018a-1**: `CREATE ROLE "paw-2018a-1" WITH LOGIN PASSWORD '<put here the password>';`
  3. Create db **paw-2019a-1**:  `CREATE DATABASE "paw-2018a-1";`
  4. Grant access to **paw-2018a-1** user: `GRANT ALL ON "paw-2018a-1" TO "paw-2018a-1";`
  5. Now close psql with `\q`
  6. On Debian, PostgreSQL uses **peer** authentication by default (this means that you can only use roles that have a corresponding unix user), to use password authentication edit `/etc/postgresql/9.6/main/pg_hba.conf` replacing where it says `peer` with `password`.
  7. Restart Postgres: `sudo service postgresql restart`
  8. Try connecting to the db running: `psql -U paw-2018a-1` (it should ask you the password)

### Setup project on IntelliJ IDEA

  1. Install IntelliJ IDEA 2019.1 (refer to IntelliJ's site for install instructions)
  2. Clone the repo to whichever folder you want
  3. Open IntelliJ IDEA and select *Import Project*
  4. Select the repo directory
  5. Mark *Import project from external model* and select Maven
  6. In the following screen mark:
      - Search for projects recursively
      - Import Maven projects automatically
      - Create module groups for multi-module Maven projects
  7. Click next twice
  8. Select Java 8 SDK (Java 9 or newer won't work)
  9. Finish import and wait for the dependencies downloads to finish (bottom right status)
  10. Create a new Run configuration (a window will open):
      1. Click on the '+' in the upper left section of the window.
      2. In the dropdown select Tomcat Server >> Local (if this option doesn't appear check if you have the Ultimate edition of IntelliJ)
      3. Click configure in Application server and add the Tomcat folder you've extracted before
      4. In the Deployment tab press '+' and add webapp:war
      5. Change the HTTP port to one that you know its free (optional)
      6. Apply
  11. Now click on play icon and it should open the browser if everything worked as expected!


