source("RQ3_Table.R")
#source("FixingDataset.R")
# For configuration 4, go to conf4 subsection in this file

#Change pdfName and conf variable for each configuration

pdfName <- "Configuration2.pdf"

pdf(pdfName,8,11)

par(mfrow=c(9,2))

par(mar=c(1.5,1.5,1,0.2),mgp=c(1.5,0.5,0))

conf <- 2

for(i in 1:18){
  
  
  window <- i
  
  df = check[check$configuration==conf,]
  df = df[df$windowSize==window,]
  
  x <- df[df$technique=='Amasaki15',]$FinalRank
  y <- df[df$technique=='Watanabe08',]$FinalRank
  z <- df[df$technique=='CamargoCruz09',]$FinalRank
  u <- df[df$technique=='Nam15',]$FinalRank
  v <- df[df$technique=='Ma12',]$FinalRank
  
  width <- 17
  height <- 5
  
  xoff <- c()
  yoff <- c()
  zoff <- c()
  uoff <- c()
  voff <- c()
  
  xoff$x <- x
  yoff$x <- y - 0.15
  zoff$x <- z + 0.15
  uoff$x <- u - 0.3
  voff$x <- v + 0.3
  
  xoff$y <- df[df$technique=='Amasaki15',]$time
  yoff$y <- df[df$technique=='Watanabe08',]$time
  zoff$y <- df[df$technique=='CamargoCruz09',]$time
  uoff$y <- df[df$technique=='Nam15',]$time
  voff$y <- df[df$technique=='Ma12',]$time
  
  plot(xoff$y, xoff$x,ylim=c(5.5,0.5), xlim=c(1,17), type = "n", 
       xlab = "", ylab = "", yaxt="n", xaxt="n")
  axis(2,seq(0,5,1),cex.axis=0.8)
  axis(2,seq(0,5,2),cex.axis=0.8)
  axis(1,seq(0,17,1),cex.axis=0.8)
  
  mtext(paste("K=",window),side = 3,line = 0, adj = 0, font=2, cex = 0.6)
  
  sapply(c(0:height), function(x) { lines(c(0:(width+1)),rep(x +
                                                               0.5,width+2),col="grey") })
  sapply(c(-2:width+2), function(x) {
    lines(c(x-0.5,x-0.5),c(0+0.5,5+0.5),col="grey") })
  
  
  
  lines(xoff$y,xoff$x,lt=2, col="green")
  lines(yoff$y,yoff$x,lt=3,col="red")
  lines(zoff$y,zoff$x,lt=4,col="blue")
  lines(uoff$y,uoff$x,lt=1,col="orange")
  lines(voff$y,voff$x,lt=6,col="purple")
  
  text(xoff$y+0.05,xoff$x+0.05,rep("A",width),cex = 0.7)
  text(yoff$y+0.05,yoff$x+0.05,rep("W",width),cex = 0.7)
  text(zoff$y+0.05,zoff$x+0.05,rep("C",width),cex = 0.7)
  text(uoff$y+0.05,uoff$x+0.05,rep("N",width),cex = 0.7)
  text(voff$y+0.05,voff$x+0.05,rep("M",width),cex = 0.7)
  
}

dev.off()

################################################
################################################
# This area is starting for configuration 4 only
###############################################
################################################

pdfName <- "Configuration4.pdf"

pdf(pdfName,7,5)

par(mfrow=c(1,1))

par(mar=c(1.5,1.5,0.2,0.2),mgp=c(1.5,0.5,0))

conf <- 4


window <- 0

df = check[check$configuration==conf,]
df = df[df$windowSize==window,]

x <- df[df$technique=='Amasaki15',]$FinalRank
y <- df[df$technique=='Watanabe08',]$FinalRank
z <- df[df$technique=='CamargoCruz09',]$FinalRank
u <- df[df$technique=='Nam15',]$FinalRank
v <- df[df$technique=='Ma12',]$FinalRank

width <- 17
height <- 5

xoff <- c()
yoff <- c()
zoff <- c()
uoff <- c()
voff <- c()

xoff$x <- x
yoff$x <- y - 0.15
zoff$x <- z + 0.15
uoff$x <- u - 0.3
voff$x <- v + 0.3

xoff$y <- df[df$technique=='Amasaki15',]$time
yoff$y <- df[df$technique=='Watanabe08',]$time
zoff$y <- df[df$technique=='CamargoCruz09',]$time
uoff$y <- df[df$technique=='Nam15',]$time
voff$y <- df[df$technique=='Ma12',]$time

par(mar=c(2.5,2.5,0.2,0.2),mgp=c(1.5,0.5,0))

plot(xoff$y, xoff$x,ylim=c(5.5,0.5), xlim=c(1,17), type = "n", 
     xlab = "Split point in time", ylab = "Rankings", yaxt="n", xaxt="n")
axis(2,seq(0,5,1),cex.axis=0.8)
axis(2,seq(0,5,2),cex.axis=0.8)
axis(1,seq(0,17,1),cex.axis=0.8)

sapply(c(0:height), function(x) { lines(c(0:(width+1)),rep(x +
                                                             0.5,width+2),col="grey") })
sapply(c(-2:width+2), function(x) {
  lines(c(x-0.5,x-0.5),c(0+0.5,5+0.5),col="grey") })

lines(xoff$y,xoff$x,lt=2, col="green")
lines(yoff$y,yoff$x,lt=3,col="red")
lines(zoff$y,zoff$x,lt=4,col="blue")
lines(uoff$y,uoff$x,lt=1,col="orange")
lines(voff$y,voff$x,lt=6,col="purple")

text(xoff$y+0.05,xoff$x+0.05,rep("A",width),cex = 0.7)
text(yoff$y+0.05,yoff$x+0.05,rep("W",width),cex = 0.7)
text(zoff$y+0.05,zoff$x+0.05,rep("C",width),cex = 0.7)
text(uoff$y+0.05,uoff$x+0.05,rep("N",width),cex = 0.7)
text(voff$y+0.05,voff$x+0.05,rep("M",width),cex = 0.7)

dev.off()
