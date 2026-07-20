CREATE SCHEMA IF NOT EXISTS probaFeladat
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER probaFeladatUser@localhost IDENTIFIED BY 'probaFeladatPass';
GRANT ALL ON probaFeladat.* TO probaFeladatUser@localhost;
USE probaFeladat;

CREATE SCHEMA IF NOT EXISTS probaFeladat_test
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
GRANT ALL ON probaFeladat_test.* TO probaFeladatUser@localhost;
USE probaFeladat_test;